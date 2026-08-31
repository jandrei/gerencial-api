package br.com.gerencial.configuracoes;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;

import java.util.Collection;
import java.util.List;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION - 1)
public class FiltroInformacoesUsuario implements ContainerRequestFilter {

    @Inject
    DadosUsuarioProvider dadosUsuarioProvider;

    @Inject
    JsonWebToken jwt;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        dadosUsuarioProvider.setHeaderOrganizacao(requestContext.getHeaderString("X-Organization"));
        dadosUsuarioProvider.setHeaderDevEmail(requestContext.getHeaderString("X-Dev-User-Email"));

        dadosUsuarioProvider.setTokenEmail(jwt.getClaim("email"));
        dadosUsuarioProvider.setTokenPermissions(getUserPermissionsFromClaim());
        dadosUsuarioProvider.setJwtToken(jwt.getRawToken());
    }

    @SuppressWarnings("unchecked")
    private List<String> getUserPermissionsFromClaim() {
        Object permissionsClaim = jwt.getClaim("permissions");

        if (permissionsClaim instanceof Collection) {
            return ((Collection<String>) permissionsClaim).stream().toList();
        } else if (permissionsClaim instanceof String) {
            return List.of((String) permissionsClaim);
        }

        return List.of();
    }

}