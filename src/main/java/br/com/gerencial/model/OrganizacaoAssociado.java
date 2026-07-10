package br.com.gerencial.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "organizacao_associados", uniqueConstraints = {
    @UniqueConstraint(name = "uk_associado_por_organizacao", columnNames = {"organizacao_id", "associado_id"})
})
public class OrganizacaoAssociado extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizacao_id", nullable = false)
    public Organizacao organizacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "associado_id", nullable = false)
    public Associado associado;

    @Column(nullable = false, length = 50)
    public String perfil;

    @Column(name = "cargo_customizado", length = 100)
    public String cargoCustomizado;

    @Column(length = 20)
    public String status = "ATIVO";

    @Column(name = "data_vinculo", insertable = false, updatable = false)
    public LocalDateTime dataVinculo;
}
