package br.com.ecommerce.domain.entity.postgres;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "code")
@Table(name = "product")
public class ProductEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 115L;
    @Id
    private String code;
    private String category;
    private String title;
    private String description;
    private BigDecimal weight;
    private LocalDate dateRegister;

}
