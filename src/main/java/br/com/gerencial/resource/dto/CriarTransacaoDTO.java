package br.com.gerencial.resource.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CriarTransacaoDTO {
    @NotNull
    public Long associadoId;
    public Long planoCobrancaId;
    @NotNull
    public BigDecimal valor;
    @NotNull
    public String descricao;
    @NotNull
    public String tipo;
    @NotNull
    public String status;
    @NotNull
    public LocalDateTime dataPagamento;
    public List<String> nomesTags;
}