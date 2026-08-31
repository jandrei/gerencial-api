package br.com.gerencial.service;

import br.com.gerencial.configuracoes.DadosUsuarioProvider;
import br.com.gerencial.model.*;
import br.com.gerencial.resource.dto.CriarTransacaoDTO;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import jakarta.ws.rs.core.SecurityContext;
import org.apache.commons.collections4.CollectionUtils;

@ApplicationScoped
public class TransacaoService {

    @Inject
    SecurityContext securityContext;
    @Inject
    DadosUsuarioProvider dadosUsuarioProvider;

    public List<Transacao> listAll(Page page) {
        List<Transacao> list = Transacao.findAll()
                .page(page)
                .list();
        return list;
    }

    public Transacao findById(Long id) {
        Transacao entity = Transacao.findById(id);
        if (entity == null) {
            throw new NotFoundException("Transação não encontrada");
        }
        return entity;
    }

    @Transactional
    public Transacao create(CriarTransacaoDTO criarTransacaoDTO) {
        Organizacao org = Organizacao.findById(dadosUsuarioProvider.getHeaderOrganizacao());
        if (org == null) {
            throw new NotFoundException("Organização não encontrada");
        }
        Transacao transacao = new Transacao();
        transacao.organizacao = org;

        if (criarTransacaoDTO.associadoId != null ) {
            Associado ass = Associado.findById(criarTransacaoDTO.associadoId);
            if (ass == null) {
                throw new NotFoundException("Associado não encontrado");
            }
            transacao.associado = ass;
        }

        if (criarTransacaoDTO.planoCobrancaId != null) {
            PlanoCobranca plano = PlanoCobranca.findById(criarTransacaoDTO.planoCobrancaId);
            if (plano == null) {
                throw new NotFoundException("Plano de Cobrança não encontrado");
            }
            transacao.planoCobranca = plano;
        }

        transacao.valor = criarTransacaoDTO.valor;
        transacao.dataPagamento = criarTransacaoDTO.dataPagamento;
        transacao.tipo = criarTransacaoDTO.tipo;
        transacao.status = criarTransacaoDTO.status;

        // 2. Busca todas as tags existentes de uma só vez (Muito mais rápido!)
        if (CollectionUtils.isNotEmpty(criarTransacaoDTO.nomesTags)) {
            List<Tag> tagsExistentes = Tag.list("descricao in ?1", criarTransacaoDTO.nomesTags);

            if (tagsExistentes.size() != criarTransacaoDTO.nomesTags.size()) {
                List<String> tagsExistentesDescricoes = tagsExistentes.stream().map(t -> t.descricao)
                        .toList();
                List<Tag> tagsNovas = criarTransacaoDTO.nomesTags.stream()
                        .filter(tagName-> !tagsExistentesDescricoes.contains(tagName))
                        .map(Tag::new)
                        .toList();
                Tag.persist(tagsNovas);
                tagsExistentes.addAll(tagsNovas);
            }

            for (Tag tag : tagsExistentes) {
                transacao.adicionarTag(tag);
            }
        }

        transacao.persist();
        return transacao;
    }

    @Transactional
    public Transacao update(Long id, Transacao entity) {
        Transacao existing = findById(id);

        if (entity.organizacao != null && entity.organizacao.id != null) {
            Organizacao org = Organizacao.findById(entity.organizacao.id);
            if (org == null) {
                throw new NotFoundException("Organização não encontrada");
            }
            existing.organizacao = org;
        }

        if (entity.associado != null) {
            if (entity.associado.id != null) {
                Associado ass = Associado.findById(entity.associado.id);
                if (ass == null) {
                    throw new NotFoundException("Associado não encontrado");
                }
                existing.associado = ass;
            } else {
                existing.associado = null;
            }
        }

        if (entity.planoCobranca != null) {
            if (entity.planoCobranca.id != null) {
                PlanoCobranca plano = PlanoCobranca.findById(entity.planoCobranca.id);
                if (plano == null) {
                    throw new NotFoundException("Plano de Cobrança não encontrado");
                }
                existing.planoCobranca = plano;
            } else {
                existing.planoCobranca = null;
            }
        }

        existing.tipo = entity.tipo;
        existing.valor = entity.valor;
        existing.descricao = entity.descricao;
        existing.dataVencimento = entity.dataVencimento;
        existing.dataPagamento = entity.dataPagamento;
        existing.status = entity.status;

        return existing;
    }

    @Transactional
    public void delete(Long id) {
        Transacao existing = findById(id);
        existing.delete();
    }
}
