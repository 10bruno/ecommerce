package br.com.ecommerce.domain.entity.postgres;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "inventory")
public class InventoryEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 114L;
    @Id
    private Integer id;
    private BigDecimal availableQuantity;
    private BigDecimal reservedQuantity;
    private String productCode;

}
