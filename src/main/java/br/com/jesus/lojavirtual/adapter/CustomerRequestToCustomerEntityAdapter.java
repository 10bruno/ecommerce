package br.com.jesus.lojavirtual.adapter;

import br.com.jesus.lojavirtual.controller.request.CustomerRequest;
import br.com.jesus.lojavirtual.domain.entity.postgres.CustomerEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerRequestToCustomerEntityAdapter {

    public CustomerEntity getCustomerEntity(CustomerRequest customerRequest) {
        return new CustomerEntity(customerRequest.getCpf(), customerRequest.getName(), customerRequest.getBirthDate(), customerRequest.getGender());
    }
}
