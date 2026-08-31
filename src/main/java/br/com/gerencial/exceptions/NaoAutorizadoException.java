package br.com.gerencial.exceptions;

import jakarta.ws.rs.core.Response;

public class NaoAutorizadoException extends RuntimeException {
    private Response.Status status;

    public NaoAutorizadoException(Response.Status status, String message) {
        super(message);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }
}
