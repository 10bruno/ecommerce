package br.com.ecommerce.adapter;

import br.com.ecommerce.controller.request.CustomerRequest;
import br.com.ecommerce.domain.entity.postgres.CustomerEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerRequestToCustomerEntityAdapter {

    public CustomerEntity getCustomerEntity(CustomerRequest customerRequest) {
        return new CustomerEntity(customerRequest.getCpf(), customerRequest.getName(), customerRequest.getBirthDate(), customerRequest.getGender());
    }
}
