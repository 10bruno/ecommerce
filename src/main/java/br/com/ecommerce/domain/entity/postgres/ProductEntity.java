package br.com.ecommerce.domain.entity.postgres;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
