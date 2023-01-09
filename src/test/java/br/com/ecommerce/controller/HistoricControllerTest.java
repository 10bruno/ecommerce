/*
package br.com.ecommerce.controller;

import br.com.ecommerce.infra.exception.HistoricDeleteException;
import br.com.ecommerce.domain.entity.mysql.HistoricEntity;
import br.com.ecommerce.domain.service.HistoricService;
import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.controller.response.HistoricResponse;
import br.com.ecommerce.infra.exception.HistoricCreateException;
import br.com.ecommerce.infra.exception.HistoricNotFoundException;
import br.com.ecommerce.util.MockBuilders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoricControllerTest {

    @InjectMocks
    private HistoricController historicController;

    @Mock
    private HistoricService historicService;

    @Test
    void shouldReturnHistory_whenSucess() throws HistoricNotFoundException {
        HistoricResponse historicResponse = MockBuilders.getHistoricResponseFirst();
        when(this.historicService.retrieveHistoric(historicResponse.getId())).thenReturn(historicResponse);

        HistoricResponse historicReturn = this.historicController.retrieveHistoric(historicResponse.getId());

        Assertions.assertEquals(historicResponse, historicReturn);
    }

    @Test
    void shouldReturnListHistorics_whenSucess() throws HistoricNotFoundException {
        List<HistoricResponse> historicResponseList = MockBuilders.buildListHistoricResponse();
        when(this.historicService.retrieveListHistorics()).thenReturn(historicResponseList);

        List<HistoricResponse> historicResponseListReturn = this.historicController.retrieveListHistorics();

        Assertions.assertEquals(historicResponseList, historicResponseListReturn);
        Assertions.assertEquals(2, historicResponseList.size());
    }

    @Test
    void shouldUpdateHistoric() throws HistoricCreateException {
        HistoricRequest historicRequest = MockBuilders.buildHistoricRequest();
        HistoricResponse historicResponse = MockBuilders.getHistoricResponseFirst();
        when(this.historicService.createHistoric(historicRequest)).thenReturn(historicResponse);

        HistoricResponse historicResponseReturn = this.historicController.updateHistoric(historicRequest);

        Assertions.assertEquals(historicResponse, historicResponseReturn);
    }

    @Test
    void shouldCreateHistoric() throws HistoricCreateException {
        HistoricRequest historicRequest = MockBuilders.buildHistoricRequest();
        HistoricResponse historicResponse = MockBuilders.getHistoricResponseSecond();
        when(this.historicService.createHistoric(historicRequest)).thenReturn(historicResponse);

        HistoricResponse historicResponseReturn = this.historicController.createHistoric(historicRequest);

        Assertions.assertEquals(historicResponse, historicResponseReturn);
    }


    @Test
    void shouldDeleteHistoric() throws HistoricDeleteException {
        HistoricEntity historicEntity = MockBuilders.buildHistoric();
        doNothing().when(this.historicService).deleteHistoric(historicEntity.getId());

        this.historicController.deleteHistoric(historicEntity.getId());

        verify(this.historicService, times(1)).deleteHistoric(historicEntity.getId());
    }

}
*/
