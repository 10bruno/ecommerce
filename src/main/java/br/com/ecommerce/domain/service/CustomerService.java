package br.com.ecommerce.domain.service;

import br.com.ecommerce.controller.response.CustomerResponse;
import br.com.ecommerce.infra.exception.CustomerCreateException;
import br.com.ecommerce.infra.exception.CustomerDeleteException;
import br.com.ecommerce.infra.exception.CustomerNotFoundException;
import br.com.ecommerce.controller.request.CustomerRequest;

import java.util.List;

public interface CustomerService {
    CustomerResponse retrieveCustomer(String cpf) throws CustomerNotFoundException;

    List<CustomerResponse> retrieveListCustomers() throws CustomerNotFoundException;

    CustomerResponse createCustomer(CustomerRequest customer) throws CustomerCreateException;

    void deleteCustomer(String cpf) throws CustomerDeleteException;
}
