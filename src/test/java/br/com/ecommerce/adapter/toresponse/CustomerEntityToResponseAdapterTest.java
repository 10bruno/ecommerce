package br.com.ecommerce.adapter.toresponse;

import br.com.ecommerce.controller.response.CustomerResponse;
import br.com.ecommerce.domain.entity.postgres.CustomerEntity;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerEntityToResponseAdapterTest {

    @InjectMocks
    private CustomerEntityToResponseAdapter adapter;

    @Test
    void shouldConvertCustomerEntityToCustomerResponse_whenValidEntity() {
        CustomerEntity customerEntity = MockBuilders.getCustomerEntity();

        CustomerResponse result = adapter.getCustomerResponse(customerEntity);

        assertNotNull(result);
        assertEquals(TestConstants.CPF, result.getCpf());
        assertEquals(TestConstants.NAME, result.getName());
        assertEquals(TestConstants.BIRTHDATE, result.getBirthDate());
        assertEquals(TestConstants.GENDER, result.getGender());
    }

    @Test
    void shouldHandleNullValues_whenEntityHasNulls() {
        CustomerEntity customerEntity = new CustomerEntity(null, null, null, null);

        CustomerResponse result = adapter.getCustomerResponse(customerEntity);

        assertNotNull(result);
        assertNull(result.getCpf());
        assertNull(result.getName());
        assertNull(result.getBirthDate());
        assertNull(result.getGender());
    }

    @Test
    void shouldConvertListOfCustomerEntities_whenValidList() {
        List<CustomerEntity> customerEntityList = List.of(
            MockBuilders.getCustomerEntity(),
            new CustomerEntity("98765432109", "Maria", "15121990", "F")
        );

        List<CustomerResponse> result = adapter.buildListCustomerResponse(customerEntityList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TestConstants.CPF, result.get(0).getCpf());
        assertEquals("98765432109", result.get(1).getCpf());
    }

    @Test
    void shouldReturnEmptyList_whenEntityListEmpty() {
        List<CustomerEntity> customerEntityList = List.of();

        List<CustomerResponse> result = adapter.buildListCustomerResponse(customerEntityList);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}