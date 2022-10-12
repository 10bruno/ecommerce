package br.com.ecommerce.service;

import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.controller.response.HistoricResponse;
import br.com.ecommerce.controller.response.exception.HistoricCreateException;
import br.com.ecommerce.controller.response.exception.HistoricNotFoundException;

import java.util.List;

public interface HistoricService {
    HistoricResponse retrieveHistoric(Integer id) throws HistoricNotFoundException;

    List<HistoricResponse> retrieveListHistorics() throws HistoricNotFoundException;

    HistoricResponse createOrUpdateHistoric(HistoricRequest historicRequest) throws HistoricCreateException;

    void deleteHistoric(Integer id);
}
