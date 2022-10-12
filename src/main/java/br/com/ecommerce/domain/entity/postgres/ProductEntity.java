package br.com.ecommerce.domain.entity.postgres;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity implements Serializable {

    @Id
    private String code;
    private String category;
    private String title;
    private String description;
    private BigDecimal weight;
    private LocalDate dateRegister;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductEntity productEntity = (ProductEntity) o;
        return Objects.equals(code, productEntity.code);
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
