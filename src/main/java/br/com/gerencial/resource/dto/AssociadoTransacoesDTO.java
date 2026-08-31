package br.com.gerencial.resource.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AssociadoTransacoesDTO(
        Long id,
        String nome,
        String email,
        String documento,
        LocalDateTime dataCadastro,
        List<TransacaoDTO> transacoes
) {}
