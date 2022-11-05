package br.com.ecommerce.domain.entity.mysql;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
public class HistoricEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 112L;

    @Id
    private Integer id;
    private String description;
    private String type;
    private LocalDate date;

}
