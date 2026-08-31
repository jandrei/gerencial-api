package br.com.gerencial.configuracoes;

import io.quarkus.runtime.LaunchMode;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class DadosUsuarioProvider {

    private String jwtToken;
    private String tokenEmail;
    private String headerDevEmail;
    private String headerOrganizacao;
    private List<String> tokenPermissions;


    public List<String> getTokenPermissions() {
        return tokenPermissions;
    }

    public void setTokenPermissions(List<String> tokenPermissions) {
        this.tokenPermissions = tokenPermissions;
    }

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

    public void setHeaderOrganizacao(String headerOrganizacao) {
        this.headerOrganizacao = headerOrganizacao;
    }

    public String getHeaderOrganizacao() {
        return headerOrganizacao;
    }

    public String getEmailFromHeaderOrFromToken() {
        if (LaunchMode.current() == LaunchMode.DEVELOPMENT || LaunchMode.current() == LaunchMode.TEST) {
            String devEmail = getDevEmailFromHeader();
            if (devEmail != null && !getDevEmailFromHeader().isEmpty()) {
                return getDevEmailFromHeader();
            }
        }

        return getTokenEmail();
    }
}
