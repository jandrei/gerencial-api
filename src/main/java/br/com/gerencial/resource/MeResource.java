package br.com.gerencial.resource;

import br.com.gerencial.model.OrganizacaoAssociado;
import br.com.gerencial.configuracoes.DadosUsuarioProvider;
import br.com.gerencial.configuracoes.TemPermissao;
import br.com.gerencial.resource.dto.OrganizacaoVinculoDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/me/organizacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeResource {

    @Inject
    DadosUsuarioProvider dadosUsuarioProvider;

    @GET
    public Response getMinhasOrganizacoes() {
        String email = dadosUsuarioProvider.getEmailFromHeaderOrFromToken();

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


}
