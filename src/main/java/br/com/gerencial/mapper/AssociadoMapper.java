package br.com.gerencial.mapper;

import br.com.gerencial.model.Associado;
import br.com.gerencial.resource.dto.AssociadoDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AssociadoMapper {

    public AssociadoDTO toDto(Associado entity) {
        if (entity == null) {
            return null;
        }
        return new AssociadoDTO(
                entity.id,
                entity.nome,
                entity.email,
                entity.documento,
                entity.dataCadastro
        );
    }

    public Associado toEntity(AssociadoDTO dto) {
        if (dto == null) {
            return null;
        }
        Associado entity = new Associado();
        entity.id = dto.id();
        entity.nome = dto.nome();
        entity.email = dto.email();
        entity.documento = dto.documento();
        return entity;
    }
}
