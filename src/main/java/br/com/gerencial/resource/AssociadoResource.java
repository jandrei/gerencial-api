package br.com.gerencial.resource;

import br.com.gerencial.model.Associado;
import br.com.gerencial.model.OrganizacaoAssociado;
import br.com.gerencial.seguranca.configuracoes.VerificarPerfil;
import br.com.gerencial.service.AssociadoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/associados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@VerificarPerfil({ "TESOUREIRO" })
public class AssociadoResource {

    @Inject
    AssociadoService service;

    // =========================================================================
    // Associado CRUD
    // =========================================================================

    @GET
    public List<Associado> listAll() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public Associado getById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(Associado entity) {
        Associado created = service.create(entity);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Associado update(@PathParam("id") Long id, Associado entity) {
        return service.update(id, entity);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }

    // =========================================================================
    // Vínculo (OrganizacaoAssociado) endpoints
    // =========================================================================

    @GET
    @Path("/vinculos")
    public List<OrganizacaoAssociado> listAllVinculos() {
        return service.listAllVinculos();
    }

    @GET
    @Path("/vinculos/{id}")
    public OrganizacaoAssociado getVinculoById(@PathParam("id") Long id) {
        return service.findVinculoById(id);
    }

    @GET
    @Path("/{id}/vinculos")
    public List<OrganizacaoAssociado> getVinculosByAssociado(@PathParam("id") Long associadoId) {
        return service.findVinculosByAssociado(associadoId);
    }

    @POST
    @Path("/vinculos")
    public Response createVinculo(OrganizacaoAssociado vinculo) {
        OrganizacaoAssociado created = service.createVinculo(vinculo);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/vinculos/{id}")
    public OrganizacaoAssociado updateVinculo(@PathParam("id") Long id, OrganizacaoAssociado vinculo) {
        return service.updateVinculo(id, vinculo);
    }

    @DELETE
    @Path("/vinculos/{id}")
    public void deleteVinculo(@PathParam("id") Long id) {
        service.deleteVinculo(id);
    }
}
