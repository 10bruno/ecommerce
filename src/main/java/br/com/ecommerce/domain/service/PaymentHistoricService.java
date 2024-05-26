package br.com.ecommerce.domain.service;

import br.com.ecommerce.controller.request.PaymentHistoricRequest;
import br.com.ecommerce.controller.response.PaymentHistoricResponse;
import br.com.ecommerce.infra.exception.PaymentHistoricCreateException;
import br.com.ecommerce.infra.exception.PaymentHistoricDeleteException;
import br.com.ecommerce.infra.exception.PaymentHistoricNotFoundException;

import java.util.List;

public interface PaymentHistoricService {
    PaymentHistoricResponse retrieveHistoric(Integer id) throws PaymentHistoricNotFoundException;

    List<PaymentHistoricResponse> retrieveListHistorics() throws PaymentHistoricNotFoundException;

    PaymentHistoricResponse createHistoric(PaymentHistoricRequest paymentHistoricRequest) throws PaymentHistoricCreateException;

    void deleteHistoric(Integer id) throws PaymentHistoricDeleteException;
}
