package br.com.gerencial.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "organizacoes")
public class Organizacao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 10, unique = true)
    public String codigo;

    @Column(name = "nome_fantasia", nullable = false, length = 150)
    public String nomeFantasia;

    @Column(name = "razao_social", length = 150)
    public String razaoSocial;

    @Column(length = 18)
    public String documento;

    @Column(name = "tipo_negocio", length = 50)
    public String tipoNegocio;

    public Boolean ativo = true;

    @Column(name = "data_cadastro", insertable = false, updatable = false)
    public LocalDateTime dataCadastro;
}
