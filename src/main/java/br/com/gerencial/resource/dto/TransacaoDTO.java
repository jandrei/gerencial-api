package br.com.gerencial.resource.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record TransacaoDTO(
        Long id,
        String tipo,
        BigDecimal valor,
        LocalDate dataVencimento,
        LocalDateTime dataPagamento,
        String status,
        String descricao,
        List<TagDTO> tags
) {}
