package br.com.jesus.lojavirtual.postgres.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "estoque")
public class Estoque implements Serializable {

    @Id
    private Integer idEstoque;
    private BigDecimal qtdDisponivel;
    private BigDecimal qtdReservado;
    private String codigoProduto;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Estoque estoque = (Estoque) o;
        return Objects.equals(idEstoque, estoque.idEstoque);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
