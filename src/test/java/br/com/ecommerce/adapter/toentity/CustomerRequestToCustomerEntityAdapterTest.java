package br.com.ecommerce.adapter.toentity;

import br.com.ecommerce.controller.request.CustomerRequest;
import br.com.ecommerce.domain.entity.postgres.CustomerEntity;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerRequestToCustomerEntityAdapterTest {

    @InjectMocks
    private CustomerRequestToCustomerEntityAdapter adapter;

    @Test
    void shouldConvertCustomerRequestToCustomerEntity_whenValidRequest() {
        CustomerRequest customerRequest = MockBuilders.buildCustomerRequest();

        CustomerEntity result = adapter.getCustomerEntity(customerRequest);

        assertNotNull(result);
        assertEquals(TestConstants.CPF, result.getCpf());
        assertEquals(TestConstants.NAME, result.getName());
        assertEquals(TestConstants.BIRTHDATE, result.getBirthDate());
        assertEquals(TestConstants.GENDER, result.getGender());
    }

    @Test
    void shouldHandleNullValues_whenRequestHasNulls() {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .cpf(null)
                .name(null)
                .birthDate(null)
                .gender(null)
                .build();

        CustomerEntity result = adapter.getCustomerEntity(customerRequest);

        assertNotNull(result);
        assertNull(result.getCpf());
        assertNull(result.getName());
        assertNull(result.getBirthDate());
        assertNull(result.getGender());
    }
}