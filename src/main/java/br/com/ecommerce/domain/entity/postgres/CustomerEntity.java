package br.com.ecommerce.domain.entity.postgres;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class CustomerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 113L;
    @Id
    private String cpf;
    private String name;
    private String birthDate;
    private String gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomerEntity customerEntity = (CustomerEntity) o;
        return Objects.equals(cpf, customerEntity.cpf);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
