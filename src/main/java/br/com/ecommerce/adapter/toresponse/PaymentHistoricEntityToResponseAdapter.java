package br.com.ecommerce.adapter.toresponse;

import br.com.ecommerce.controller.response.PaymentHistoricResponse;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentHistoricEntityToResponseAdapter {
    public PaymentHistoricResponse getHistoricResponse(PaymentHistoricEntity paymentHistoricEntity) {
        return PaymentHistoricResponse.builder()
                .id(paymentHistoricEntity.getId())
                .type(paymentHistoricEntity.getType())
                .description(paymentHistoricEntity.getDescription())
                .date(paymentHistoricEntity.getDate())
                .build();
    }

    public List<PaymentHistoricResponse> buildListHistoricResponse(List<PaymentHistoricEntity> paymentHistoricEntityList) {
        return paymentHistoricEntityList.stream()
                .map(this::getHistoricResponse)
                .toList();
    }
}
