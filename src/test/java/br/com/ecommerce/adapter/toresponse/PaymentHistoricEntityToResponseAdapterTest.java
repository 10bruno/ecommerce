package br.com.ecommerce.adapter.toresponse;

import br.com.ecommerce.controller.response.PaymentHistoricResponse;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentHistoricEntityToResponseAdapterTest {

    @InjectMocks
    private PaymentHistoricEntityToResponseAdapter adapter;

    @Test
    void shouldConvertPaymentHistoricEntityToPaymentHistoricResponse_whenValidEntity() {
        PaymentHistoricEntity paymentHistoricEntity = MockBuilders.buildHistoric();

        PaymentHistoricResponse result = adapter.getHistoricResponse(paymentHistoricEntity);

        assertNotNull(result);
        assertEquals(TestConstants.ID_1, result.getId());
        assertEquals(TestConstants.FULL_DESCRIPTION, result.getDescription());
        assertEquals(TestConstants.DEBIT_TYPE, result.getType());
        assertNotNull(result.getDate());
    }

    @Test
    void shouldHandleNullValues_whenEntityHasNulls() {
        PaymentHistoricEntity paymentHistoricEntity = new PaymentHistoricEntity(null, null, null, null);

        PaymentHistoricResponse result = adapter.getHistoricResponse(paymentHistoricEntity);

        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getDescription());
        assertNull(result.getType());
        assertNull(result.getDate());
    }

    @Test
    void shouldConvertListOfPaymentHistoricEntities_whenValidList() {
        List<PaymentHistoricEntity> paymentHistoricEntityList = List.of(
            MockBuilders.buildHistoric(),
            new PaymentHistoricEntity(2, "Credit payment", TestConstants.CREDIT_TYPE, LocalDate.now())
        );

        List<PaymentHistoricResponse> result = adapter.buildListHistoricResponse(paymentHistoricEntityList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TestConstants.ID_1, result.get(0).getId());
        assertEquals(Integer.valueOf(2), result.get(1).getId());
        assertEquals(TestConstants.DEBIT_TYPE, result.get(0).getType());
        assertEquals(TestConstants.CREDIT_TYPE, result.get(1).getType());
    }

    @Test
    void shouldReturnEmptyList_whenEntityListEmpty() {
        List<PaymentHistoricEntity> paymentHistoricEntityList = List.of();

        List<PaymentHistoricResponse> result = adapter.buildListHistoricResponse(paymentHistoricEntityList);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}