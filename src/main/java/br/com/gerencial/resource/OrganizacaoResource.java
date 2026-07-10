package br.com.gerencial.resource;

import br.com.gerencial.model.Organizacao;
import br.com.gerencial.service.OrganizacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/organizacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizacaoResource {

    @Inject
    OrganizacaoService service;

    @GET
    public List<Organizacao> listAll() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public Organizacao getById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(Organizacao entity) {
        Organizacao created = service.create(entity);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Organizacao update(@PathParam("id") Long id, Organizacao entity) {
        return service.update(id, entity);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
