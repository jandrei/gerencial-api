package br.com.gerencial.resource;

import br.com.gerencial.model.Associado;
import br.com.gerencial.model.Tag;
import br.com.gerencial.model.Transacao;
import br.com.gerencial.resource.dto.AssociadoTransacoesDTO;
import br.com.gerencial.resource.dto.TagDTO;
import br.com.gerencial.resource.dto.TransacaoDTO;
import br.com.gerencial.resource.dto.UsuariosTransacoesFiltroDTO;
import br.com.gerencial.service.OrganizacaoService;
import br.com.gerencial.service.UsuariosTransacaosService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Path("/api/usuarios-transacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuariosTransacoesController {

    @Inject
    OrganizacaoService organizacaoService;
    @Inject
    UsuariosTransacaosService usuariosTransacaosService;

    @GET
    public java.util.List<AssociadoTransacoesDTO> listaUsuariosETransacoes(
            @BeanParam UsuariosTransacoesFiltroDTO filtro) {

        var organizacao = organizacaoService.getOrganizacaoUsuarioLogado();
        return usuariosTransacaosService.listAll(organizacao, filtro)
                .stream().map(this::mapUsuarios)
                .toList();
    }

    private @NonNull AssociadoTransacoesDTO mapUsuarios(Associado u) {
        var transacoes = (u.transacoes == null) ? List.<TransacaoDTO>of() :
                u.transacoes.stream()
                        .map(this::mapTransacoes)
                        .toList();

        return new AssociadoTransacoesDTO(
                u.id,
                u.nome,
                u.email,
                u.documento,
                u.dataCadastro,
                transacoes
        );
    }

    private @NonNull TransacaoDTO mapTransacoes(Transacao t) {
        var tagsDto = (t.tags == null) ? List.<TagDTO>of() :
                t.tags.stream().map(this::mapTag).toList();

        return new TransacaoDTO(
                t.id,
                t.tipo,
                t.valor,
                t.dataVencimento,
                t.dataPagamento,
                t.status,
                t.descricao,
                tagsDto
        );
    }

    private TagDTO mapTag(Tag tag) {
        return new TagDTO(tag.id, tag.descricao);
    }

}
