package br.com.ecommerce.domain.entity.mysql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "payment_historic")
public class PaymentHistoricEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 112L;

    @Id
    private Integer id;
    private String description;
    private String type;
    private LocalDate date;

}
