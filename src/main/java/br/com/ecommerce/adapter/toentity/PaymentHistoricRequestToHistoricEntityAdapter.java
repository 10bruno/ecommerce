package br.com.ecommerce.adapter.toentity;

import br.com.ecommerce.controller.request.PaymentHistoricRequest;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentHistoricRequestToHistoricEntityAdapter {
    public PaymentHistoricEntity getHistoricEntity(PaymentHistoricRequest paymentHistoricRequest) {
        return new PaymentHistoricEntity(paymentHistoricRequest.getId(), paymentHistoricRequest.getDescription(), paymentHistoricRequest.getType(), paymentHistoricRequest.getDate());
    }
}
