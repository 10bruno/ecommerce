package br.com.jesus.lojavirtual.domain.entity.mysql;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_historic")
public class HistoricEntity implements Serializable {

    @Id
    private Integer id;
    private String description;
    private String type;
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HistoricEntity historicEntity = (HistoricEntity) o;
        return Objects.equals(id, historicEntity.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
