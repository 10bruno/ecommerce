package br.com.jesus.lojavirtual.service.impl;

import br.com.jesus.lojavirtual.adapter.CustomerEntityToResponseAdapter;
import br.com.jesus.lojavirtual.adapter.CustomerRequestToCustomerEntityAdapter;
import br.com.jesus.lojavirtual.controller.request.CustomerRequest;
import br.com.jesus.lojavirtual.controller.response.CustomerResponse;
import br.com.jesus.lojavirtual.controller.response.exception.CustomerCreateException;
import br.com.jesus.lojavirtual.controller.response.exception.CustomerNotFoundException;
import br.com.jesus.lojavirtual.domain.entity.postgres.CustomerEntity;
import br.com.jesus.lojavirtual.enumerated.MessageEnum;
import br.com.jesus.lojavirtual.domain.repository.postgres.CustomerRepository;
import br.com.jesus.lojavirtual.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerRequestToCustomerEntityAdapter customerRequestToCustomerEntityAdapter;
    private final CustomerEntityToResponseAdapter customerEntityToResponseAdapter;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerEntityToResponseAdapter customerEntityToResponseAdapter, CustomerRequestToCustomerEntityAdapter customerRequestToCustomerEntityAdapter) {
        this.customerRepository = customerRepository;
        this.customerEntityToResponseAdapter = customerEntityToResponseAdapter;
        this.customerRequestToCustomerEntityAdapter = customerRequestToCustomerEntityAdapter;
    }

    @Override
    public CustomerResponse retrieveCustomer(String cpf) throws CustomerNotFoundException {
        CustomerEntity customerEntity =
                customerRepository.findById(cpf)
                        .orElseThrow(() -> new CustomerNotFoundException(MessageEnum.CUSTOMER_NOT_FOUND_EXCEPTION.getValue()));

        return customerEntityToResponseAdapter.getCustomerResponse(customerEntity);
    }

    @Override
    public List<CustomerResponse> retrieveListCustomers() throws CustomerNotFoundException {
        List<CustomerEntity> customerEntityList =
                Optional.of(this.customerRepository.findAll())
                        .orElseThrow(() -> new CustomerNotFoundException(MessageEnum.CUSTOMERS_LIST_NOT_FOUND_EXCEPTION.getValue()));

        return customerEntityToResponseAdapter.buildListCustomerResponse(customerEntityList);
    }

    @Override
    public CustomerResponse createOrUpdateCustomer(CustomerRequest customerRequest) throws CustomerCreateException {
        CustomerEntity customerEntity = customerRequestToCustomerEntityAdapter.getCustomerEntity(customerRequest);

        CustomerEntity customerEntitySaved =
                Optional.of(this.customerRepository.save(customerEntity))
                        .orElseThrow(() -> new CustomerCreateException(MessageEnum.CUSTOMER_ERROR_ON_CREATE_EXCEPTION.getValue()));

        return customerEntityToResponseAdapter.getCustomerResponse(customerEntitySaved);
    }

    @Override
    public void deleteCustomer(String cpf) {
        this.customerRepository.deleteById(cpf);
    }
}
