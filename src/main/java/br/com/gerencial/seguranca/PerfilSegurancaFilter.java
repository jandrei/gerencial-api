package br.com.gerencial.seguranca;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.runtime.LaunchMode;
import br.com.gerencial.configuracoes.VerificarPerfil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import br.com.gerencial.model.OrganizacaoAssociado;

/**
 * Resumo da ópera:
 * O Admin faz tudo: Tratado direto na linha 42 do filtro (if
 * ("ADMIN".equals(perfilDoUsuario)) return;). Ele ignora a restrição do método
 * e passa.
 * 
 * O Tesoureiro gerencia: Nas rotas de cadastros de usuários, alteração de dados
 * do clube ou de transações, você coloca @VerificarPerfil({"TESOUREIRO"}).
 * 
 * O Associado é limitado: Nas rotas financeiras, ele tem acesso
 * ao @VerificarPerfil({"TESOUREIRO", "ASSOCIADO"}), mas dentro do método GET
 * você faz um if simples: se for associado, injeta o e-mail dele no filtro do
 * banco de dados para ele nunca ver o saldo ou as contas dos outros membros.
 */
@Provider
@VerificarPerfil({}) // Vincula o filtro à nossa anotação
public class PerfilSegurancaFilter implements ContainerRequestFilter {

    @Inject
    JsonWebToken jwt;

    @Context
    ResourceInfo resourceInfo; // Permite ler os parâmetros da anotação no método

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Garante que o filtro controle apenas rotas sob /api
        String path = requestContext.getUriInfo().getPath();
        if (true) {
            return;
        }

        // 1. Recupera o e-mail do Token do Google ou do Header de Dev em modo de
        // desenvolvimento
        String email = null;
        if (LaunchMode.current() == LaunchMode.DEVELOPMENT || LaunchMode.current() == LaunchMode.TEST) {
            String devEmail = requestContext.getHeaderString("X-Dev-User-Email");
            if (devEmail != null && !devEmail.isEmpty()) {
                email = devEmail;
            }
        }

        if (email == null) {
            email = jwt.getClaim("email");
        }

        if (email == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // 2. Recupera a Organização Atual enviada pelo Frontend
        String codigoOrganizacao = requestContext.getHeaderString("X-Organization-Id");
        if (codigoOrganizacao == null || codigoOrganizacao.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity("O cabeçalho X-Organization-Id é obrigatório.").build());
            return;
        }

        // 3. Descobre quais perfis o método atual aceita
        VerificarPerfil anotacao = resourceInfo.getResourceMethod().getAnnotation(VerificarPerfil.class);
        List<String> perfisPermitidos = Arrays.asList(anotacao.value());

        // 4. Consulta o Banco de Dados (Usando o Panache)
        // Aqui buscamos o perfil do associado especificamente nesta organização
        String perfilDoUsuario = buscarPerfilNoBanco(email, codigoOrganizacao);

        if (perfilDoUsuario == null) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Você não tem vínculo com esta organização.").build());
            return;
        }

        // Regra Especial: Se for ADMIN global da organização, faz tudo de forma
        // irrestrita
        if ("ADMIN".equals(perfilDoUsuario)) {
            return; // Acesso liberado
        }

        // 5. Valida se o perfil do usuário está na lista dos permitidos para a API
        if (!perfisPermitidos.contains(perfilDoUsuario)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Seu perfil (" + perfilDoUsuario + ") não tem permissão para esta ação.").build());
        }
    }

    private String buscarPerfilNoBanco(String email, String codigoOrganizacao) {
        // Consulta no banco de dados usando Hibernate/Panache HQL para buscar o vínculo
        // do associado
        Optional<OrganizacaoAssociado> vinculo = OrganizacaoAssociado.find(
                "associado.email = ?1 and organizacao.codigo = ?2 and status = 'ATIVO'",
                email, codigoOrganizacao).firstResultOptional();

        return vinculo.map(oa -> oa.perfil).orElse(null);
    }
}