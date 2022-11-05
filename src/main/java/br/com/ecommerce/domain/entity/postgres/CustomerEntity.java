package br.com.ecommerce.domain.entity.postgres;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cpf")
@Table(name = "customer")
public class CustomerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 113L;
    @Id
    private String cpf;
    private String name;
    private String birthDate;
    private String gender;

}
