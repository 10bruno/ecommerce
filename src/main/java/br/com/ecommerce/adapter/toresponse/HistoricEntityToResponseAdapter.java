package br.com.ecommerce.adapter.toresponse;

import br.com.ecommerce.controller.response.HistoricResponse;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricEntityToResponseAdapter {
    public HistoricResponse getHistoricResponse(PaymentHistoricEntity paymentHistoricEntity) {
        return HistoricResponse.builder()
                .id(paymentHistoricEntity.getId())
                .type(paymentHistoricEntity.getType())
                .description(paymentHistoricEntity.getDescription())
                .date(paymentHistoricEntity.getDate())
                .build();
    }

    public List<HistoricResponse> buildListHistoricResponse(List<PaymentHistoricEntity> paymentHistoricEntityList) {
        return paymentHistoricEntityList.stream()
                .map(this::getHistoricResponse)
                .toList();
    }
}
