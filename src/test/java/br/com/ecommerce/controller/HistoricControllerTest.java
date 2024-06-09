/*
package br.com.ecommerce.controller;

import br.com.ecommerce.infra.exception.PaymentHistoricDeleteException;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import br.com.ecommerce.domain.service.PaymentHistoricService;
import br.com.ecommerce.controller.request.PaymentHistoricRequest;
import br.com.ecommerce.controller.response.PaymentHistoricResponse;
import br.com.ecommerce.infra.exception.PaymentHistoricCreateException;
import br.com.ecommerce.infra.exception.PaymentHistoricNotFoundException;
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
    private PaymentHistoricController historicController;

    @Mock
    private PaymentHistoricService historicService;

    @Test
    void shouldReturnHistory_whenSucess() throws PaymentHistoricNotFoundException {
        PaymentHistoricResponse historicResponse = MockBuilders.getHistoricResponseFirst();
        when(this.historicService.retrieveHistoric(historicResponse.getId())).thenReturn(historicResponse);

        PaymentHistoricResponse historicReturn = this.historicController.retrieveHistoric(historicResponse.getId());

        Assertions.assertEquals(historicResponse, historicReturn);
    }

    @Test
    void shouldReturnListHistorics_whenSucess() throws PaymentHistoricNotFoundException {
        List<PaymentHistoricResponse> historicResponseList = MockBuilders.buildListHistoricResponse();
        when(this.historicService.retrieveListHistorics()).thenReturn(historicResponseList);

        List<PaymentHistoricResponse> historicResponseListReturn = this.historicController.retrieveListHistorics();

        Assertions.assertEquals(historicResponseList, historicResponseListReturn);
        Assertions.assertEquals(2, historicResponseList.size());
    }

    @Test
    void shouldUpdateHistoric() throws PaymentHistoricCreateException {
        PaymentHistoricRequest historicRequest = MockBuilders.buildHistoricRequest();
        PaymentHistoricResponse historicResponse = MockBuilders.getHistoricResponseFirst();
        when(this.historicService.createHistoric(historicRequest)).thenReturn(historicResponse);

        PaymentHistoricResponse historicResponseReturn = this.historicController.updateHistoric(historicRequest);

        Assertions.assertEquals(historicResponse, historicResponseReturn);
    }

    @Test
    void shouldCreateHistoric() throws PaymentHistoricCreateException {
        PaymentHistoricRequest historicRequest = MockBuilders.buildHistoricRequest();
        PaymentHistoricResponse historicResponse = MockBuilders.getHistoricResponseSecond();
        when(this.historicService.createHistoric(historicRequest)).thenReturn(historicResponse);

        PaymentHistoricResponse historicResponseReturn = this.historicController.createHistoric(historicRequest);

        Assertions.assertEquals(historicResponse, historicResponseReturn);
    }


    @Test
    void shouldDeleteHistoric() throws PaymentHistoricDeleteException {
        PaymentHistoricEntity historicEntity = MockBuilders.buildHistoric();
        doNothing().when(this.historicService).deleteHistoric(historicEntity.getId());

        this.historicController.deleteHistoric(historicEntity.getId());

        verify(this.historicService, times(1)).deleteHistoric(historicEntity.getId());
    }

}
*/
