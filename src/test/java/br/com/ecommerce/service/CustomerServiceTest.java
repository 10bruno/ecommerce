package br.com.ecommerce.service;

import br.com.ecommerce.adapter.toentity.CustomerRequestToCustomerEntityAdapter;
import br.com.ecommerce.adapter.toresponse.CustomerEntityToResponseAdapter;
import br.com.ecommerce.controller.common.enumerated.MessageEnum;
import br.com.ecommerce.controller.response.CustomerResponse;
import br.com.ecommerce.domain.entity.postgres.CustomerEntity;
import br.com.ecommerce.domain.repository.postgres.CustomerRepository;
import br.com.ecommerce.domain.service.impl.CustomerServiceImpl;
import br.com.ecommerce.infra.exception.CustomerNotFoundException;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerRequestToCustomerEntityAdapter customerRequestToCustomerEntityAdapter;

    @Mock
    private CustomerEntityToResponseAdapter customerEntityToResponseAdapter;

    @Test
    void shouldReturnACustomerEntity_whenReturnOk() throws CustomerNotFoundException {
        CustomerEntity customerEntity = MockBuilders.getCustomerEntity();
        CustomerResponse customerResponse = MockBuilders.getCustomerResponse();

        when(repository.findById(TestConstants.CPF)).thenReturn(Optional.of(customerEntity));
        when(customerEntityToResponseAdapter.getCustomerResponse(customerEntity)).thenReturn(customerResponse);

        customerService.retrieveCustomer(TestConstants.CPF);

        verify(customerEntityToResponseAdapter, times(1)).getCustomerResponse(customerEntity);
    }

    @Test
    void shouldReturnACustomerException_whenCustomerNotFound() {
        CustomerNotFoundException exception =
                assertThrows(CustomerNotFoundException.class, () ->
                        customerService.retrieveCustomer(TestConstants.CPF));

        assertEquals(MessageEnum.CUSTOMER_NOT_FOUND_EXCEPTION.getValue(), exception.getMessage());
    }

}
