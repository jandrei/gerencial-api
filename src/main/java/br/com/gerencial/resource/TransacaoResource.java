package br.com.gerencial.resource;

import br.com.gerencial.model.Transacao;
import br.com.gerencial.configuracoes.TemPermissao;
import br.com.gerencial.resource.dto.CriarTransacaoDTO;
import br.com.gerencial.service.TransacaoService;
import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/transacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransacaoResource {

    @Inject
    TransacaoService service;

    @GET
    public List<Transacao> listAll(@QueryParam("index") int index, @QueryParam("size ") int size) {
        return service.listAll(Page.of(index, size));
    }

    @GET
    @Path("/{id}")
    @TemPermissao(permissoes = "transacao:get")
    public Transacao getById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @TemPermissao(permissoes = "transacao:add")
    public Response create(CriarTransacaoDTO criarTransacaoDTO) {
        Transacao created = service.create(criarTransacaoDTO);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @TemPermissao(permissoes = "transacao:modify")
    public Transacao update(@PathParam("id") Long id, Transacao entity) {
        return service.update(id, entity);
    }

    @DELETE
    @Path("/{id}")
    @TemPermissao(permissoes = "transacao:delete")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
