package br.com.gerencial.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "associados")
public class Associado extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 100)
    public String nome;

    @Column(nullable = false, unique = true, length = 100)
    public String email;

    @Column(length = 14)
    public String documento;

    @Column(name = "data_cadastro", insertable = false, updatable = false)
    public LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "associado")
    public Set<Transacao> transacoes = new HashSet<>();
}

