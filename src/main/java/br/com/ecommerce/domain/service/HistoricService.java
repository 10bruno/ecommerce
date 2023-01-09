package br.com.ecommerce.domain.service;

import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.controller.response.HistoricResponse;
import br.com.ecommerce.infra.exception.HistoricCreateException;
import br.com.ecommerce.infra.exception.HistoricDeleteException;
import br.com.ecommerce.infra.exception.HistoricNotFoundException;

import java.util.List;

public interface HistoricService {
    HistoricResponse retrieveHistoric(Integer id) throws HistoricNotFoundException;

    List<HistoricResponse> retrieveListHistorics() throws HistoricNotFoundException;

    HistoricResponse createHistoric(HistoricRequest historicRequest) throws HistoricCreateException;

    void deleteHistoric(Integer id) throws HistoricDeleteException;
}
