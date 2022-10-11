package br.com.jesus.lojavirtual.adapter;

import br.com.jesus.lojavirtual.controller.response.HistoricResponse;
import br.com.jesus.lojavirtual.domain.entity.mysql.HistoricEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricEntityToResponseAdapter {
    public HistoricResponse getHistoricResponse(HistoricEntity historicEntity) {
        return HistoricResponse.builder()
                .id(historicEntity.getId())
                .type(historicEntity.getType())
                .description(historicEntity.getDescription())
                .date(historicEntity.getDate())
                .build();
    }

    public List<HistoricResponse> buildListHistoricResponse(List<HistoricEntity> historicEntityList) {
        return historicEntityList.stream()
                .map(this::getHistoricResponse)
                .toList();
    }
}
