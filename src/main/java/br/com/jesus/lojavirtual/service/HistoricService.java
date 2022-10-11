package br.com.jesus.lojavirtual.service;

import br.com.jesus.lojavirtual.controller.request.HistoricRequest;
import br.com.jesus.lojavirtual.controller.response.HistoricResponse;
import br.com.jesus.lojavirtual.controller.response.exception.HistoricCreateException;
import br.com.jesus.lojavirtual.controller.response.exception.HistoricNotFoundException;

import java.util.List;

public interface HistoricService {
    HistoricResponse retrieveHistoric(Integer id) throws HistoricNotFoundException;

    List<HistoricResponse> retrieveListHistorics() throws HistoricNotFoundException;

    HistoricResponse createOrUpdateHistoric(HistoricRequest historicRequest) throws HistoricCreateException;

    void deleteHistoric(Integer id);
}
