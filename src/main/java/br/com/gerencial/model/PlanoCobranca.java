package br.com.gerencial.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "planos_cobranca")
public class PlanoCobranca extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizacao_id", nullable = false)
    public Organizacao organizacao;

    @Column(nullable = false, length = 100)
    public String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal valor;

    @Column(nullable = false, length = 20)
    public String frequencia;

    @Column(name = "data_vencimento_padrao")
    public LocalDate dataVencimentoPadrao;
}
