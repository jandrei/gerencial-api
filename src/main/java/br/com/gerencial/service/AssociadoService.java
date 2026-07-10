package br.com.gerencial.service;

import br.com.gerencial.model.Associado;
import br.com.gerencial.model.Organizacao;
import br.com.gerencial.model.OrganizacaoAssociado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class AssociadoService {

    // =========================================================================
    // Associado Service Logic
    // =========================================================================

    public List<Associado> listAll() {
        return Associado.listAll();
    }

    public Associado findById(Long id) {
        Associado entity = Associado.findById(id);
        if (entity == null) {
            throw new NotFoundException("Associado não encontrado");
        }
        return entity;
    }

    @Transactional
    public Associado create(Associado entity) {
        if (entity == null || entity.id != null) {
            throw new BadRequestException("ID não deve ser enviado em requisição POST");
        }
        entity.persist();
        return entity;
    }

    @Transactional
    public Associado update(Long id, Associado entity) {
        Associado existing = findById(id);
        existing.nome = entity.nome;
        existing.email = entity.email;
        existing.documento = entity.documento;
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        Associado existing = findById(id);
        existing.delete();
    }

    // =========================================================================
    // Vínculo (OrganizacaoAssociado) Service Logic
    // =========================================================================

    public List<OrganizacaoAssociado> listAllVinculos() {
        return OrganizacaoAssociado.listAll();
    }

    public OrganizacaoAssociado findVinculoById(Long id) {
        OrganizacaoAssociado entity = OrganizacaoAssociado.findById(id);
        if (entity == null) {
            throw new NotFoundException("Vínculo não encontrado");
        }
        return entity;
    }

    public List<OrganizacaoAssociado> findVinculosByAssociado(Long associadoId) {
        // Verify associado exists first
        findById(associadoId);
        return OrganizacaoAssociado.list("associado.id", associadoId);
    }

    @Transactional
    public OrganizacaoAssociado createVinculo(OrganizacaoAssociado vinculo) {
        if (vinculo == null || vinculo.id != null) {
            throw new BadRequestException("ID não deve ser enviado em requisição POST");
        }
        if (vinculo.organizacao == null || vinculo.organizacao.id == null) {
            throw new BadRequestException("Organização é obrigatória");
        }
        if (vinculo.associado == null || vinculo.associado.id == null) {
            throw new BadRequestException("Associado é obrigatório");
        }

        Organizacao org = Organizacao.findById(vinculo.organizacao.id);
        if (org == null) {
            throw new NotFoundException("Organização não encontrada");
        }
        Associado ass = Associado.findById(vinculo.associado.id);
        if (ass == null) {
            throw new NotFoundException("Associado não encontrado");
        }

        vinculo.organizacao = org;
        vinculo.associado = ass;
        vinculo.persist();
        return vinculo;
    }

    @Transactional
    public OrganizacaoAssociado updateVinculo(Long id, OrganizacaoAssociado vinculo) {
        OrganizacaoAssociado existing = findVinculoById(id);
        existing.perfil = vinculo.perfil;
        existing.cargoCustomizado = vinculo.cargoCustomizado;
        existing.status = vinculo.status;
        return existing;
    }

    @Transactional
    public void deleteVinculo(Long id) {
        OrganizacaoAssociado existing = findVinculoById(id);
        existing.delete();
    }
}
