package br.com.gerencial.mapper;

import br.com.gerencial.model.Associado;
import br.com.gerencial.model.Organizacao;
import br.com.gerencial.model.OrganizacaoAssociado;
import br.com.gerencial.resource.dto.OrganizacaoAssociadoDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrganizacaoAssociadoMapper {

    public OrganizacaoAssociadoDTO toDto(OrganizacaoAssociado entity) {
        if (entity == null) {
            return null;
        }
        return new OrganizacaoAssociadoDTO(
                entity.id,
                entity.organizacao != null ? entity.organizacao.id : null,
                entity.associado != null ? entity.associado.id : null,
                entity.perfil,
                entity.cargoCustomizado,
                entity.status,
                entity.dataVinculo
        );
    }

    public OrganizacaoAssociado toEntity(OrganizacaoAssociadoDTO dto) {
        if (dto == null) {
            return null;
        }
        OrganizacaoAssociado entity = new OrganizacaoAssociado();
        entity.id = dto.id();
        if (dto.organizacaoId() != null) {
            Organizacao organizacao = new Organizacao();
            organizacao.id = dto.organizacaoId();
            entity.organizacao = organizacao;
        }
        if (dto.associadoId() != null) {
            Associado associado = new Associado();
            associado.id = dto.associadoId();
            entity.associado = associado;
        }
        entity.perfil = dto.perfil();
        entity.cargoCustomizado = dto.cargoCustomizado();
        if (dto.status() != null) {
            entity.status = dto.status();
        }
        return entity;
    }
}
