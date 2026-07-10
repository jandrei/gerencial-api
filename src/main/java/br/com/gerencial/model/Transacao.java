package br.com.gerencial.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
public class Transacao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizacao_id", nullable = false)
    public Organizacao organizacao;

    @ManyToOne
    @JoinColumn(name = "associado_id")
    public Associado associado;

    @ManyToOne
    @JoinColumn(name = "plano_cobranca_id")
    public PlanoCobranca planoCobranca;

    @Column(nullable = false, length = 10)
    public String tipo;

    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal valor;

    @Column(columnDefinition = "TEXT")
    public String descricao;

    @Column(name = "data_vencimento", nullable = false)
    public LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    public LocalDateTime dataPagamento;

    @Column(length = 20)
    public String status = "PENDENTE";
}
