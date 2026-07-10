package br.com.gerencial.service;

import br.com.gerencial.model.Associado;
import br.com.gerencial.model.Organizacao;
import br.com.gerencial.model.PlanoCobranca;
import br.com.gerencial.model.Transacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public class TransacaoService {

    @Inject
    SecurityContext securityContext;

    public List<Transacao> listAll() {
        return Transacao.listAll();
    }

    public Transacao findById(Long id) {
        Transacao entity = Transacao.findById(id);
        if (entity == null) {
            throw new NotFoundException("Transação não encontrada");
        }
        return entity;
    }

    @Transactional
    public Transacao create(Transacao entity) {
        if (entity == null || entity.id != null) {
            throw new BadRequestException("ID não deve ser enviado em requisição POST");
        }
        if (entity.organizacao == null || entity.organizacao.id == null) {
            throw new BadRequestException("Organização é obrigatória");
        }

        Organizacao org = Organizacao.findById(entity.organizacao.id);
        if (org == null) {
            throw new NotFoundException("Organização não encontrada");
        }
        entity.organizacao = org;

        if (entity.associado != null && entity.associado.id != null) {
            Associado ass = Associado.findById(entity.associado.id);
            if (ass == null) {
                throw new NotFoundException("Associado não encontrado");
            }
            entity.associado = ass;
        }

        if (entity.planoCobranca != null && entity.planoCobranca.id != null) {
            PlanoCobranca plano = PlanoCobranca.findById(entity.planoCobranca.id);
            if (plano == null) {
                throw new NotFoundException("Plano de Cobrança não encontrado");
            }
            entity.planoCobranca = plano;
        }

        entity.persist();
        return entity;
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
