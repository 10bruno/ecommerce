package br.com.ecommerce.service;

import br.com.ecommerce.adapter.toentity.PaymentHistoricRequestToHistoricEntityAdapter;
import br.com.ecommerce.adapter.toresponse.PaymentHistoricEntityToResponseAdapter;
import br.com.ecommerce.controller.common.enumerated.MessageEnum;
import br.com.ecommerce.controller.request.PaymentHistoricRequest;
import br.com.ecommerce.controller.response.PaymentHistoricResponse;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import br.com.ecommerce.domain.repository.mysql.PaymentHistoricRepository;
import br.com.ecommerce.domain.service.impl.PaymentHistoricServiceImpl;
import br.com.ecommerce.infra.exception.PaymentHistoricCreateException;
import br.com.ecommerce.infra.exception.PaymentHistoricDeleteException;
import br.com.ecommerce.infra.exception.PaymentHistoricNotFoundException;
import br.com.ecommerce.util.MockBuilders;
import br.com.ecommerce.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentHistoricServiceTest {

    @InjectMocks
    private PaymentHistoricServiceImpl paymentHistoricService;

    @Mock
    private PaymentHistoricRepository paymentHistoricRepository;

    @Mock
    private PaymentHistoricRequestToHistoricEntityAdapter paymentHistoricRequestToHistoricEntityAdapter;

    @Mock
    private PaymentHistoricEntityToResponseAdapter paymentHistoricEntityToResponseAdapter;

    @Test
    void shouldReturnPaymentHistoricResponse_whenRetrieveHistoricSuccess() throws PaymentHistoricNotFoundException {
        PaymentHistoricEntity paymentHistoricEntity = MockBuilders.buildHistoric();
        PaymentHistoricResponse paymentHistoricResponse = MockBuilders.getHistoricResponseFirst();

        when(paymentHistoricRepository.findById(TestConstants.ID_1)).thenReturn(Optional.of(paymentHistoricEntity));
        when(paymentHistoricEntityToResponseAdapter.getHistoricResponse(paymentHistoricEntity)).thenReturn(paymentHistoricResponse);

        PaymentHistoricResponse result = paymentHistoricService.retrieveHistoric(TestConstants.ID_1);

        assertNotNull(result);
        assertEquals(paymentHistoricResponse, result);
        verify(paymentHistoricRepository, times(1)).findById(TestConstants.ID_1);
        verify(paymentHistoricEntityToResponseAdapter, times(1)).getHistoricResponse(paymentHistoricEntity);
    }

    @Test
    void shouldThrowPaymentHistoricNotFoundException_whenHistoricNotFound() {
        when(paymentHistoricRepository.findById(TestConstants.ID_1)).thenReturn(Optional.empty());

        PaymentHistoricNotFoundException exception = assertThrows(PaymentHistoricNotFoundException.class, 
            () -> paymentHistoricService.retrieveHistoric(TestConstants.ID_1));

        assertEquals(MessageEnum.PAYMENT_HISTORIC_NOT_FOUND_EXCEPTION.getValue(), exception.getMessage());
        verify(paymentHistoricRepository, times(1)).findById(TestConstants.ID_1);
        verify(paymentHistoricEntityToResponseAdapter, never()).getHistoricResponse(any());
    }

    @Test
    void shouldReturnPaymentHistoricList_whenRetrieveListHistoricsSuccess() throws PaymentHistoricNotFoundException {
        List<PaymentHistoricEntity> paymentHistoricEntityList = List.of(MockBuilders.buildHistoric());
        List<PaymentHistoricResponse> paymentHistoricResponseList = MockBuilders.buildListHistoricResponse();

        when(paymentHistoricRepository.findAll()).thenReturn(paymentHistoricEntityList);
        when(paymentHistoricEntityToResponseAdapter.buildListHistoricResponse(paymentHistoricEntityList)).thenReturn(paymentHistoricResponseList);

        List<PaymentHistoricResponse> result = paymentHistoricService.retrieveListHistorics();

        assertNotNull(result);
        assertEquals(paymentHistoricResponseList, result);
        verify(paymentHistoricRepository, times(1)).findAll();
        verify(paymentHistoricEntityToResponseAdapter, times(1)).buildListHistoricResponse(paymentHistoricEntityList);
    }

    @Test
    void shouldThrowPaymentHistoricNotFoundException_whenHistoricListEmpty() {
        when(paymentHistoricRepository.findAll()).thenReturn(new ArrayList<>());

        PaymentHistoricNotFoundException exception = assertThrows(PaymentHistoricNotFoundException.class, 
            () -> paymentHistoricService.retrieveListHistorics());

        assertEquals(MessageEnum.PAYMENT_HISTORIC_LIST_NOT_FOUND_EXCEPTION.getValue(), exception.getMessage());
        verify(paymentHistoricRepository, times(1)).findAll();
        verify(paymentHistoricEntityToResponseAdapter, never()).buildListHistoricResponse(any());
    }

    @Test
    void shouldCreatePaymentHistoric_whenCreateHistoricSuccess() throws PaymentHistoricCreateException {
        PaymentHistoricRequest paymentHistoricRequest = MockBuilders.buildPaymentHistoricRequest();
        PaymentHistoricEntity paymentHistoricEntity = MockBuilders.buildHistoric();
        PaymentHistoricEntity savedPaymentHistoricEntity = MockBuilders.buildHistoric();
        PaymentHistoricResponse paymentHistoricResponse = MockBuilders.getHistoricResponseFirst();

        when(paymentHistoricRequestToHistoricEntityAdapter.getHistoricEntity(any(PaymentHistoricRequest.class))).thenReturn(paymentHistoricEntity);
        when(paymentHistoricRepository.save(paymentHistoricEntity)).thenReturn(savedPaymentHistoricEntity);
        when(paymentHistoricEntityToResponseAdapter.getHistoricResponse(savedPaymentHistoricEntity)).thenReturn(paymentHistoricResponse);

        PaymentHistoricResponse result = paymentHistoricService.createHistoric(paymentHistoricRequest);

        assertNotNull(result);
        assertEquals(paymentHistoricResponse, result);
        
        ArgumentCaptor<PaymentHistoricRequest> requestCaptor = ArgumentCaptor.forClass(PaymentHistoricRequest.class);
        verify(paymentHistoricRequestToHistoricEntityAdapter, times(1)).getHistoricEntity(requestCaptor.capture());
        
        PaymentHistoricRequest capturedRequest = requestCaptor.getValue();
        assertEquals(LocalDate.now(), capturedRequest.getDate());
        
        verify(paymentHistoricRepository, times(1)).save(paymentHistoricEntity);
        verify(paymentHistoricEntityToResponseAdapter, times(1)).getHistoricResponse(savedPaymentHistoricEntity);
    }

    @Test
    void shouldThrowPaymentHistoricCreateException_whenCreateHistoricFails() {
        PaymentHistoricRequest paymentHistoricRequest = MockBuilders.buildPaymentHistoricRequest();
        PaymentHistoricEntity paymentHistoricEntity = MockBuilders.buildHistoric();
        Exception repositoryException = new RuntimeException("Database error");

        when(paymentHistoricRequestToHistoricEntityAdapter.getHistoricEntity(any(PaymentHistoricRequest.class))).thenReturn(paymentHistoricEntity);
        when(paymentHistoricRepository.save(paymentHistoricEntity)).thenThrow(repositoryException);

        PaymentHistoricCreateException exception = assertThrows(PaymentHistoricCreateException.class, 
            () -> paymentHistoricService.createHistoric(paymentHistoricRequest));

        assertEquals(MessageEnum.PAYMENT_HISTORIC_ERROR_ON_CREATE_EXCEPTION.getValue(), exception.getMessage());
        assertEquals(repositoryException, exception.getCause());
        verify(paymentHistoricRequestToHistoricEntityAdapter, times(1)).getHistoricEntity(any(PaymentHistoricRequest.class));
        verify(paymentHistoricRepository, times(1)).save(paymentHistoricEntity);
        verify(paymentHistoricEntityToResponseAdapter, never()).getHistoricResponse(any());
    }

    @Test
    void shouldDeletePaymentHistoric_whenDeleteHistoricSuccess() throws PaymentHistoricDeleteException {
        doNothing().when(paymentHistoricRepository).deleteById(TestConstants.ID_1);

        assertDoesNotThrow(() -> paymentHistoricService.deleteHistoric(TestConstants.ID_1));
        
        verify(paymentHistoricRepository, times(1)).deleteById(TestConstants.ID_1);
    }

    @Test
    void shouldThrowPaymentHistoricDeleteException_whenDeleteHistoricFails() {
        Exception repositoryException = new RuntimeException("Database error");
        doThrow(repositoryException).when(paymentHistoricRepository).deleteById(TestConstants.ID_1);

        PaymentHistoricDeleteException exception = assertThrows(PaymentHistoricDeleteException.class, 
            () -> paymentHistoricService.deleteHistoric(TestConstants.ID_1));

        assertEquals(MessageEnum.PAYMENT_HISTORIC_ERROR_ON_DELETE_EXCEPTION.getValue(), exception.getMessage());
        assertEquals(repositoryException, exception.getCause());
        verify(paymentHistoricRepository, times(1)).deleteById(TestConstants.ID_1);
    }
}