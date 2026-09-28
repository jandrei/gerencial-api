package br.com.gerencial.resource;

import br.com.gerencial.configuracoes.TemPermissao;
import br.com.gerencial.mapper.AssociadoMapper;
import br.com.gerencial.mapper.OrganizacaoAssociadoMapper;
import br.com.gerencial.resource.dto.AssociadoDTO;
import br.com.gerencial.resource.dto.OrganizacaoAssociadoDTO;
import br.com.gerencial.service.AssociadoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/associados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@TemPermissao(perfis = { "TESOUREIRO" })
public class AssociadoResource {

    @Inject
    AssociadoService service;

    @Inject
    AssociadoMapper associadoMapper;

    @Inject
    OrganizacaoAssociadoMapper vinculoMapper;

    @GET
    public List<AssociadoDTO> listAll() {
        return service.listAll().stream().map(associadoMapper::toDto).toList();
    }

    @GET
    @Path("/{id}")
    public AssociadoDTO getById(@PathParam("id") Long id) {
        return associadoMapper.toDto(service.findById(id));
    }

    @POST
    public Response create(AssociadoDTO dto) {
        var created = service.create(associadoMapper.toEntity(dto));
        return Response.status(Response.Status.CREATED).entity(associadoMapper.toDto(created)).build();
    }

    @PUT
    @Path("/{id}")
    public AssociadoDTO update(@PathParam("id") Long id, AssociadoDTO dto) {
        return associadoMapper.toDto(service.update(id, associadoMapper.toEntity(dto)));
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
    public List<OrganizacaoAssociadoDTO> listAllVinculos() {
        return service.listAllVinculos().stream().map(vinculoMapper::toDto).toList();
    }

    @GET
    @Path("/vinculos/{id}")
    public OrganizacaoAssociadoDTO getVinculoById(@PathParam("id") Long id) {
        return vinculoMapper.toDto(service.findVinculoById(id));
    }

    @GET
    @Path("/{id}/vinculos")
    public List<OrganizacaoAssociadoDTO> getVinculosByAssociado(@PathParam("id") Long associadoId) {
        return service.findVinculosByAssociado(associadoId).stream().map(vinculoMapper::toDto).toList();
    }

    @POST
    @Path("/vinculos")
    public Response createVinculo(OrganizacaoAssociadoDTO dto) {
        var created = service.createVinculo(vinculoMapper.toEntity(dto));
        return Response.status(Response.Status.CREATED).entity(vinculoMapper.toDto(created)).build();
    }

    @PUT
    @Path("/vinculos/{id}")
    public OrganizacaoAssociadoDTO updateVinculo(@PathParam("id") Long id, OrganizacaoAssociadoDTO dto) {
        return vinculoMapper.toDto(service.updateVinculo(id, vinculoMapper.toEntity(dto)));
    }

    @DELETE
    @Path("/vinculos/{id}")
    public void deleteVinculo(@PathParam("id") Long id) {
        service.deleteVinculo(id);
    }
}
