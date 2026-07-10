package br.com.gerencial.service;

import br.com.gerencial.model.Organizacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class OrganizacaoService {

    public List<Organizacao> listAll() {
        return Organizacao.listAll();
    }

    public Organizacao findById(Long id) {
        Organizacao entity = Organizacao.findById(id);
        if (entity == null) {
            throw new NotFoundException("Organização não encontrada");
        }
        return entity;
    }

    @Transactional
    public Organizacao create(Organizacao entity) {
        if (entity == null || entity.id != null) {
            throw new BadRequestException("ID não deve ser enviado em requisição POST");
        }
        entity.persist();
        return entity;
    }

    @Transactional
    public Organizacao update(Long id, Organizacao entity) {
        Organizacao existing = findById(id);
        existing.codigo = entity.codigo;
        existing.nomeFantasia = entity.nomeFantasia;
        existing.razaoSocial = entity.razaoSocial;
        existing.documento = entity.documento;
        existing.tipoNegocio = entity.tipoNegocio;
        existing.ativo = entity.ativo;
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        Organizacao existing = findById(id);
        existing.delete();
    }
}
