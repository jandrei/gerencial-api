package br.com.gerencial.seguranca.configuracoes;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class UserInfoService {

    private String tokenEmail;
    private String headerDevEmail;
    private String jwtToken;
    private String headerOrganizacaoId;

    public String getDevEmailFromHeader() {
        return headerDevEmail;
    }

    public void setHeaderDevEmail(String headerDevEmail) {
        this.headerDevEmail = headerDevEmail;
    }

    public void setTokenEmail(String tokenEmail) {
        this.tokenEmail = tokenEmail;
    }

    public String getTokenEmail() {
        return tokenEmail;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setHeaderOrganizacaoId(String headerOrganizacaoId) {
        this.headerOrganizacaoId = headerOrganizacaoId;
    }

    public String getHeaderOrganizacaoId() {
        return headerOrganizacaoId;
    }
}
