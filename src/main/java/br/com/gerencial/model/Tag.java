package br.com.gerencial.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag extends PanacheEntityBase {

    public Tag(String descricao){
        this.descricao = descricao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 50)
    public String descricao;

    // Relacionamento bidirecional mapeado pelo atributo "tags" na classe Transacao
    @ManyToMany(mappedBy = "tags")
    public List<Transacao> transacoes = new ArrayList<>();

}
