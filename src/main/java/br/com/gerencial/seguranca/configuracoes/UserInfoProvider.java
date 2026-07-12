package br.com.gerencial.seguranca.configuracoes;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION - 1)
public class UserInfoProvider implements ContainerRequestFilter {

    @Inject
    UserInfoService userinfoServide;

    @Inject
    JsonWebToken jwt;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        userinfoServide.setHeaderOrganizacaoId(requestContext.getHeaderString("X-Organization-Id"));
        userinfoServide.setHeaderDevEmail(requestContext.getHeaderString("X-Dev-User-Email"));

        userinfoServide.setTokenEmail(jwt.getClaim("email"));
        userinfoServide.setJwtToken(jwt.getRawToken());

    }
}