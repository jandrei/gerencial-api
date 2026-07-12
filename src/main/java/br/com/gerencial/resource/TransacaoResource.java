package br.com.gerencial.resource;

import br.com.gerencial.model.Transacao;
import br.com.gerencial.seguranca.configuracoes.VerificarPerfil;
import br.com.gerencial.service.TransacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/transacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@VerificarPerfil({ "TESOUREIRO" })
public class TransacaoResource {

    @Inject
    TransacaoService service;

    @GET
    public List<Transacao> listAll() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public Transacao getById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(Transacao entity) {
        Transacao created = service.create(entity);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Transacao update(@PathParam("id") Long id, Transacao entity) {
        return service.update(id, entity);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
