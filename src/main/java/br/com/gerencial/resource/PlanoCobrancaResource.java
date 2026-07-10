package br.com.gerencial.resource;

import br.com.gerencial.model.PlanoCobranca;
import br.com.gerencial.service.PlanoCobrancaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/planos-cobranca")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlanoCobrancaResource {

    @Inject
    PlanoCobrancaService service;

    @GET
    public List<PlanoCobranca> listAll() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public PlanoCobranca getById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(PlanoCobranca entity) {
        PlanoCobranca created = service.create(entity);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public PlanoCobranca update(@PathParam("id") Long id, PlanoCobranca entity) {
        return service.update(id, entity);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
