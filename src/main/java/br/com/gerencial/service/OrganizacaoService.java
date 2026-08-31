package br.com.gerencial.service;

import br.com.gerencial.configuracoes.DadosUsuarioProvider;
import br.com.gerencial.exceptions.NaoAutorizadoException;
import br.com.gerencial.model.Organizacao;
import br.com.gerencial.model.OrganizacaoAssociado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class OrganizacaoService {

    @Inject
    DadosUsuarioProvider dadosUsuarioProvider;

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

    public Organizacao getOrganizacaoUsuarioLogado() {
        String email = dadosUsuarioProvider.getEmailFromHeaderOrFromToken();
        String organizacao = dadosUsuarioProvider.getHeaderOrganizacao();

        if (email == null) {
            throw new NaoAutorizadoException(Response.Status.UNAUTHORIZED,
                    "Usuário não autenticado no provedor de identidade.");
        }

        // Busca todos os vínculos ativos do associado por e-mail
        List<OrganizacaoAssociado> vinculos = OrganizacaoAssociado
                .find("""
                    associado.email = ?1
                    AND organizacao.codigo = ?2
                    AND status = 'ATIVO'
                """, email, organizacao)
                .list();
        return vinculos.getFirst().organizacao;
    }
}
