package br.com.ecommerce.util;

import br.com.ecommerce.domain.entity.mysql.HistoricEntity;
import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.controller.response.HistoricResponse;

import java.time.LocalDate;
import java.util.List;

public class MockBuilders {

    public static HistoricEntity buildHistoric() {
        HistoricEntity historicEntity = new HistoricEntity();
        historicEntity.setId(TestConstants.ID_1);
        historicEntity.setType(TestConstants.DEBIT_TYPE);
        historicEntity.setDate(LocalDate.now());
        historicEntity.setDescription(TestConstants.FULL_DESCRIPTION);
        return historicEntity;
    }

    public static HistoricResponse getHistoricResponseFirst() {
        return HistoricResponse.builder()
                .id(TestConstants.ID_1)
                .type(TestConstants.DEBIT_TYPE)
                .description(TestConstants.FULL_DESCRIPTION)
                .date(LocalDate.now())
                .build();
    }

    public static HistoricResponse getHistoricResponseSecond() {
        return HistoricResponse.builder()
                .id(TestConstants.ID_2)
                .type(TestConstants.CREDIT_TYPE)
                .description(TestConstants.FULL_DESCRIPTION)
                .date(LocalDate.now())
                .build();
    }

    public static List<HistoricResponse> buildListHistoricResponse() {
        return List.of(getHistoricResponseFirst(), getHistoricResponseSecond());
    }

    public static HistoricRequest buildHistoricRequest() {
        return HistoricRequest
                .builder()
                .id(TestConstants.ID_1)
                .type(TestConstants.DEBIT_TYPE)
                .description(TestConstants.PARTIAL_DESCRIPTION)
                .date(LocalDate.now())
                .build();
    }

}
