package br.com.jesus.lojavirtual.adapter;

import br.com.jesus.lojavirtual.controller.request.HistoricRequest;
import br.com.jesus.lojavirtual.domain.entity.mysql.HistoricEntity;
import org.springframework.stereotype.Service;

@Service
public class HistoricRequestToHistoricEntityAdapter {

    public HistoricEntity getHistoricEntity(HistoricRequest historicRequest) {
        return new HistoricEntity(historicRequest.getId(), historicRequest.getDescription(), historicRequest.getType(), historicRequest.getDate());
    }
}
