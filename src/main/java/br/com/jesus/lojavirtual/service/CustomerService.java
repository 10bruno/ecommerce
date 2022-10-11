package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.controller.request.CustomerRequest;
import br.com.jesus.lojavirtual.controller.response.CustomerResponse;
import br.com.jesus.lojavirtual.controller.response.exception.CustomerCreateException;
import br.com.jesus.lojavirtual.controller.response.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    CustomerResponse retrieveCustomer(String cpf) throws CustomerNotFoundException;

    List<CustomerResponse> retrieveListCustomers() throws CustomerNotFoundException;

    CustomerResponse createOrUpdateCustomer(CustomerRequest customer) throws CustomerCreateException;

    void deleteCustomer(String cpf);
}
