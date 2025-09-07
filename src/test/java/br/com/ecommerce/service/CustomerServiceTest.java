package br.com.ecommerce.service;

import br.com.ecommerce.adapter.toentity.CustomerRequestToCustomerEntityAdapter;
import br.com.ecommerce.adapter.toresponse.CustomerEntityToResponseAdapter;
import br.com.ecommerce.controller.common.enumerated.MessageEnum;
import br.com.ecommerce.controller.request.CustomerRequest;
import br.com.ecommerce.controller.response.CustomerResponse;
import br.com.ecommerce.domain.entity.postgres.CustomerEntity;
import br.com.ecommerce.domain.repository.postgres.CustomerRepository;
import br.com.ecommerce.domain.service.impl.CustomerServiceImpl;
import br.com.ecommerce.infra.exception.CustomerCreateException;
import br.com.ecommerce.infra.exception.CustomerDeleteException;
import br.com.ecommerce.infra.exception.CustomerNotFoundException;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerRequestToCustomerEntityAdapter customerRequestToCustomerEntityAdapter;

    @Mock
    private CustomerEntityToResponseAdapter customerEntityToResponseAdapter;

    @Test
    void shouldReturnCustomerResponse_whenRetrieveCustomerSuccess() throws CustomerNotFoundException {
        CustomerEntity customerEntity = MockBuilders.getCustomerEntity();
        CustomerResponse customerResponse = MockBuilders.getCustomerResponse();

        when(customerRepository.findById(TestConstants.CPF)).thenReturn(Optional.of(customerEntity));
        when(customerEntityToResponseAdapter.getCustomerResponse(customerEntity)).thenReturn(customerResponse);

        CustomerResponse result = customerService.retrieveCustomer(TestConstants.CPF);

        assertNotNull(result);
        assertEquals(customerResponse, result);
        verify(customerRepository, times(1)).findById(TestConstants.CPF);
        verify(customerEntityToResponseAdapter, times(1)).getCustomerResponse(customerEntity);
    }

    @Test
    void shouldThrowCustomerNotFoundException_whenCustomerNotFound() {
        when(customerRepository.findById(TestConstants.CPF)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, 
            () -> customerService.retrieveCustomer(TestConstants.CPF));

        assertEquals(MessageEnum.CUSTOMER_NOT_FOUND_EXCEPTION.getValue(), exception.getMessage());
        verify(customerRepository, times(1)).findById(TestConstants.CPF);
        verify(customerEntityToResponseAdapter, never()).getCustomerResponse(any());
    }

    @Test
    void shouldReturnCustomerList_whenRetrieveListCustomersSuccess() throws CustomerNotFoundException {
        List<CustomerEntity> customerEntityList = List.of(MockBuilders.getCustomerEntity());
        List<CustomerResponse> customerResponseList = MockBuilders.buildListCustomerResponse();

        when(customerRepository.findAll()).thenReturn(customerEntityList);
        when(customerEntityToResponseAdapter.buildListCustomerResponse(customerEntityList)).thenReturn(customerResponseList);

        List<CustomerResponse> result = customerService.retrieveListCustomers();

        assertNotNull(result);
        assertEquals(customerResponseList, result);
        verify(customerRepository, times(1)).findAll();
        verify(customerEntityToResponseAdapter, times(1)).buildListCustomerResponse(customerEntityList);
    }

    @Test
    void shouldThrowCustomerNotFoundException_whenCustomerListEmpty() {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, 
            () -> customerService.retrieveListCustomers());

        assertEquals(MessageEnum.CUSTOMERS_LIST_NOT_FOUND_EXCEPTION.getValue(), exception.getMessage());
        verify(customerRepository, times(1)).findAll();
        verify(customerEntityToResponseAdapter, never()).buildListCustomerResponse(any());
    }

    @Test
    void shouldCreateCustomer_whenCreateCustomerSuccess() throws CustomerCreateException {
        CustomerRequest customerRequest = MockBuilders.buildCustomerRequest();
        CustomerEntity customerEntity = MockBuilders.getCustomerEntity();
        CustomerEntity savedCustomerEntity = MockBuilders.getCustomerEntity();
        CustomerResponse customerResponse = MockBuilders.getCustomerResponse();

        when(customerRequestToCustomerEntityAdapter.getCustomerEntity(customerRequest)).thenReturn(customerEntity);
        when(customerRepository.save(customerEntity)).thenReturn(savedCustomerEntity);
        when(customerEntityToResponseAdapter.getCustomerResponse(savedCustomerEntity)).thenReturn(customerResponse);

        CustomerResponse result = customerService.createCustomer(customerRequest);

        assertNotNull(result);
        assertEquals(customerResponse, result);
        verify(customerRequestToCustomerEntityAdapter, times(1)).getCustomerEntity(customerRequest);
        verify(customerRepository, times(1)).save(customerEntity);
        verify(customerEntityToResponseAdapter, times(1)).getCustomerResponse(savedCustomerEntity);
    }

    @Test
    void shouldThrowCustomerCreateException_whenCreateCustomerFails() {
        CustomerRequest customerRequest = MockBuilders.buildCustomerRequest();
        CustomerEntity customerEntity = MockBuilders.getCustomerEntity();
        Exception repositoryException = new RuntimeException("Database error");

        when(customerRequestToCustomerEntityAdapter.getCustomerEntity(customerRequest)).thenReturn(customerEntity);
        when(customerRepository.save(customerEntity)).thenThrow(repositoryException);

        CustomerCreateException exception = assertThrows(CustomerCreateException.class, 
            () -> customerService.createCustomer(customerRequest));

        assertEquals(MessageEnum.CUSTOMER_ERROR_ON_CREATE_EXCEPTION.getValue(), exception.getMessage());
        assertEquals(repositoryException, exception.getCause());
        verify(customerRequestToCustomerEntityAdapter, times(1)).getCustomerEntity(customerRequest);
        verify(customerRepository, times(1)).save(customerEntity);
        verify(customerEntityToResponseAdapter, never()).getCustomerResponse(any());
    }

    @Test
    void shouldDeleteCustomer_whenDeleteCustomerSuccess() throws CustomerDeleteException {
        doNothing().when(customerRepository).deleteById(TestConstants.CPF);

        assertDoesNotThrow(() -> customerService.deleteCustomer(TestConstants.CPF));
        
        verify(customerRepository, times(1)).deleteById(TestConstants.CPF);
    }

    @Test
    void shouldThrowCustomerDeleteException_whenDeleteCustomerFails() {
        Exception repositoryException = new RuntimeException("Database error");
        doThrow(repositoryException).when(customerRepository).deleteById(TestConstants.CPF);

        CustomerDeleteException exception = assertThrows(CustomerDeleteException.class, 
            () -> customerService.deleteCustomer(TestConstants.CPF));

        assertEquals(MessageEnum.CUSTOMER_ERROR_ON_DELETE_EXCEPTION.getValue(), exception.getMessage());
        assertEquals(repositoryException, exception.getCause());
        verify(customerRepository, times(1)).deleteById(TestConstants.CPF);
    }
}
