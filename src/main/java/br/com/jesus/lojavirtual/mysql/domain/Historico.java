package br.com.jesus.lojavirtual.mysql.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "historico_pagamentos")
public class Historico implements Serializable {

    @Id
    private Integer id;
    private String descricao;
    private String tipo;
    private LocalDate data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Historico historico = (Historico) o;
        return Objects.equals(id, historico.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
