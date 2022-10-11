package br.com.jesus.lojavirtual.domain.entity.postgres;

import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
public class InventoryEntity implements Serializable {

    @Id
    private Integer id;
    private BigDecimal availableQuantity;
    private BigDecimal reservedQuantity;
    private String productCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InventoryEntity inventoryEntity = (InventoryEntity) o;
        return Objects.equals(id, inventoryEntity.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
