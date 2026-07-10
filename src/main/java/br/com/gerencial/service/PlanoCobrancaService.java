package br.com.gerencial.service;

import br.com.gerencial.model.Organizacao;
import br.com.gerencial.model.PlanoCobranca;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class PlanoCobrancaService {

    public List<PlanoCobranca> listAll() {
        return PlanoCobranca.listAll();
    }

    public PlanoCobranca findById(Long id) {
        PlanoCobranca entity = PlanoCobranca.findById(id);
        if (entity == null) {
            throw new NotFoundException("Plano de Cobrança não encontrado");
        }
        return entity;
    }

    @Transactional
    public PlanoCobranca create(PlanoCobranca entity) {
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
        entity.persist();
        return entity;
    }

    @Transactional
    public PlanoCobranca update(Long id, PlanoCobranca entity) {
        PlanoCobranca existing = findById(id);
        if (entity.organizacao != null && entity.organizacao.id != null) {
            Organizacao org = Organizacao.findById(entity.organizacao.id);
            if (org == null) {
                throw new NotFoundException("Organização não encontrada");
            }
            existing.organizacao = org;
        }
        existing.nome = entity.nome;
        existing.valor = entity.valor;
        existing.frequencia = entity.frequencia;
        existing.dataVencimentoPadrao = entity.dataVencimentoPadrao;
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        PlanoCobranca existing = findById(id);
        existing.delete();
    }
}
