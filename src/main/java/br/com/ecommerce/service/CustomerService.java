package br.com.ecommerce.service;

import br.com.ecommerce.controller.response.CustomerResponse;
import br.com.ecommerce.controller.response.exception.CustomerCreateException;
import br.com.ecommerce.controller.response.exception.CustomerNotFoundException;
import br.com.ecommerce.controller.request.CustomerRequest;

import java.util.List;

public interface CustomerService {
    CustomerResponse retrieveCustomer(String cpf) throws CustomerNotFoundException;

    List<CustomerResponse> retrieveListCustomers() throws CustomerNotFoundException;

    CustomerResponse createOrUpdateCustomer(CustomerRequest customer) throws CustomerCreateException;

    void deleteCustomer(String cpf);
}
