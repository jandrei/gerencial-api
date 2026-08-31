package br.com.gerencial.service;

import br.com.gerencial.configuracoes.DadosUsuarioProvider;
import br.com.gerencial.model.Associado;
import br.com.gerencial.model.Organizacao;
import br.com.gerencial.resource.dto.UsuariosTransacoesFiltroDTO;
import br.com.gerencial.resource.dto.AssociadoTransacoesDTO;
import br.com.gerencial.resource.dto.TransacaoDTO;
import br.com.gerencial.resource.dto.TagDTO;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UsuariosTransacaosService {

    @Inject
    SecurityContext securityContext;
    @Inject
    DadosUsuarioProvider dadosUsuarioProvider;

    public List<Associado> listAll(Organizacao organizacao,
                                   UsuariosTransacoesFiltroDTO filtro) {
        Page page = filtro.getPage();
        Long userId = filtro.userId();
        LocalDateTime from = filtro.fromDateTime();
        LocalDateTime to = filtro.toDateTime();
        List<Long> tagIds = filtro.tagIds();
        boolean requireAllTags = filtro.requireAllTags();

        StringBuilder jpql = new StringBuilder("""
                            select distinct u
                            from Associado u
                            left join fetch u.transacoes t
                            left join fetch t.tags tag
                        """);

        List<String> cond = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        if (organizacao != null && organizacao.id != null) {
            // filter by organization membership via subquery to avoid fetch-owner problems
            cond.add("exists (select 1 from OrganizacaoAssociado oa where oa.associado = u and oa.organizacao.id = :orgId)");
            params.put("orgId", organizacao.id);
        }

        if (userId != null) {
            cond.add("u.id = :userId");
            params.put("userId", userId);
        }

        if (from != null) {
            cond.add("t.dataPagamento >= :from");
            params.put("from", from);
        }

        if (to != null) {
            cond.add("t.dataPagamento <= :to");
            params.put("to", to);
        }

        if (tagIds != null && !tagIds.isEmpty()) {
            if (requireAllTags) {
                String sub = "t.id in (select tx.id from Transacao tx join tx.tags tg where tg.id in :tagIds group by tx.id having count(distinct tg.id) = :tagCount)";
                cond.add(sub);
                params.put("tagIds", tagIds);
                params.put("tagCount", tagIds.size());
            } else {
                cond.add("tag.id in :tagIds");
                params.put("tagIds", tagIds);
            }
        }

        if (!cond.isEmpty()) {
            jpql.append(" where ").append(String.join(" and ", cond));
        }

        return Associado.find(jpql.toString(), params)
                .page(page)
                .list();
    }

}
