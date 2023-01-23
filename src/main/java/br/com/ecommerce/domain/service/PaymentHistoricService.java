package br.com.ecommerce.domain.service;

import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.controller.response.HistoricResponse;
import br.com.ecommerce.infra.exception.PaymentHistoricCreateException;
import br.com.ecommerce.infra.exception.PaymentHistoricDeleteException;
import br.com.ecommerce.infra.exception.PaymentHistoricNotFoundException;

import java.util.List;

public interface PaymentHistoricService {
    HistoricResponse retrieveHistoric(Integer id) throws PaymentHistoricNotFoundException;

    List<HistoricResponse> retrieveListHistorics() throws PaymentHistoricNotFoundException;

    HistoricResponse createHistoric(HistoricRequest historicRequest) throws PaymentHistoricCreateException;

    void deleteHistoric(Integer id) throws PaymentHistoricDeleteException;
}
