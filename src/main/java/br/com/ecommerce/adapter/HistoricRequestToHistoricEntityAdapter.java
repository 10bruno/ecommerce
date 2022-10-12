package br.com.ecommerce.adapter;

import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.domain.entity.mysql.HistoricEntity;
import org.springframework.stereotype.Service;

@Service
public class HistoricRequestToHistoricEntityAdapter {

    public HistoricEntity getHistoricEntity(HistoricRequest historicRequest) {
        return new HistoricEntity(historicRequest.getId(), historicRequest.getDescription(), historicRequest.getType(), historicRequest.getDate());
    }
}
