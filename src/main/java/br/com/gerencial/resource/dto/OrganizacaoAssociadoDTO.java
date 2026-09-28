package br.com.gerencial.resource.dto;

import java.time.LocalDateTime;

public record OrganizacaoAssociadoDTO(
        Long id,
        Long organizacaoId,
        Long associadoId,
        String perfil,
        String cargoCustomizado,
        String status,
        LocalDateTime dataVinculo
) {}
