package br.com.gerencial.resource.dto;

import java.time.LocalDateTime;

public record AssociadoDTO(
        Long id,
        String nome,
        String email,
        String documento,
        LocalDateTime dataCadastro
) {}
