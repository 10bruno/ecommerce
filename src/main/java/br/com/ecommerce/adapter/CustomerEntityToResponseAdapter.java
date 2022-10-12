package br.com.ecommerce.adapter;

import br.com.ecommerce.controller.response.CustomerResponse;
import br.com.ecommerce.domain.entity.postgres.CustomerEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerEntityToResponseAdapter {
    public CustomerResponse getCustomerResponse(CustomerEntity customerEntity) {
        return CustomerResponse.builder()
                .cpf(customerEntity.getCpf())
                .name(customerEntity.getName())
                .birthDate(customerEntity.getBirthDate())
                .gender(customerEntity.getGender())
                .build();
    }

    public List<CustomerResponse> buildListCustomerResponse(List<CustomerEntity> customerEntityList) {
        return customerEntityList.stream()
                .map(this::getCustomerResponse)
                .toList();
    }

}
