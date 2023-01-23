package br.com.ecommerce.adapter.toentity;

import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import org.springframework.stereotype.Service;

@Service
public class HistoricRequestToHistoricEntityAdapter {

    public PaymentHistoricEntity getHistoricEntity(HistoricRequest historicRequest) {
        return new PaymentHistoricEntity(historicRequest.getId(), historicRequest.getDescription(), historicRequest.getType(), historicRequest.getDate());
    }
}
