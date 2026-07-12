package br.com.gerencial.resource;

import br.com.gerencial.model.OrganizacaoAssociado;
import br.com.gerencial.seguranca.configuracoes.UserInfoService;
import br.com.gerencial.seguranca.configuracoes.VerificarPerfil;
import io.quarkus.runtime.LaunchMode;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/me/organizacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@VerificarPerfil
public class MeResource {

    @Inject
    UserInfoService userInfoService;

    @GET
    public Response getMinhasOrganizacoes() {
        String email = null;

        // Em modo DEV/TEST, permite passar o e-mail pelo header para testes locais
        if (LaunchMode.current() == LaunchMode.DEVELOPMENT || LaunchMode.current() == LaunchMode.TEST) {
            String devEmail = userInfoService.getDevEmailFromHeader();
            if (devEmail != null && !userInfoService.getDevEmailFromHeader().isEmpty()) {
                email = userInfoService.getDevEmailFromHeader();
            }
        }

        if (email == null) {
            email = userInfoService.getTokenEmail();
        }

        if (email == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Usuário não autenticado no provedor de identidade.").build();
        }

        // Busca todos os vínculos ativos do associado por e-mail
        List<OrganizacaoAssociado> vinculos = OrganizacaoAssociado.find(
                "associado.email = ?1 and status = 'ATIVO'", email).list();

        // Converte para DTO para simplificar o payload e evitar recursão infinita na
        // serialização do JPA
        List<OrganizacaoVinculoDTO> dtos = vinculos.stream()
                .map(v -> new OrganizacaoVinculoDTO(
                        v.organizacao.codigo,
                        v.organizacao.nomeFantasia,
                        v.perfil))
                .collect(Collectors.toList());

        return Response.ok(dtos).build();
    }

    public static class OrganizacaoVinculoDTO {
        public String codigo;
        public String nomeFantasia;
        public String perfil;

        public OrganizacaoVinculoDTO() {
        }

        public OrganizacaoVinculoDTO(String codigo, String nomeFantasia, String perfil) {
            this.codigo = codigo;
            this.nomeFantasia = nomeFantasia;
            this.perfil = perfil;
        }
    }
}
