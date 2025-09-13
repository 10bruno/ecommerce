package br.com.ecommerce.adapter.toentity;

import br.com.ecommerce.controller.request.PaymentHistoricRequest;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentHistoricRequestToHistoricEntityAdapterTest {

    @InjectMocks
    private PaymentHistoricRequestToHistoricEntityAdapter adapter;

    @Test
    void shouldConvertPaymentHistoricRequestToPaymentHistoricEntity_whenValidRequest() {
        PaymentHistoricRequest paymentHistoricRequest = MockBuilders.buildPaymentHistoricRequest();

        PaymentHistoricEntity result = adapter.getHistoricEntity(paymentHistoricRequest);

        assertNotNull(result);
        assertEquals(TestConstants.ID_1, result.getId());
        assertEquals(TestConstants.PARTIAL_DESCRIPTION, result.getDescription());
        assertEquals(TestConstants.DEBIT_TYPE, result.getType());
        assertNotNull(result.getDate());
    }

    @Test
    void shouldHandleNullValues_whenRequestHasNulls() {
        PaymentHistoricRequest paymentHistoricRequest = PaymentHistoricRequest.builder()
                .id(null)
                .description(null)
                .type(null)
                .date(null)
                .build();

        PaymentHistoricEntity result = adapter.getHistoricEntity(paymentHistoricRequest);

        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getDescription());
        assertNull(result.getType());
        assertNull(result.getDate());
    }
}