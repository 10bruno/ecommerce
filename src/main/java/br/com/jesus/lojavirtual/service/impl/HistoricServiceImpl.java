package br.com.jesus.lojavirtual.service.impl;

import br.com.jesus.lojavirtual.adapter.HistoricEntityToResponseAdapter;
import br.com.jesus.lojavirtual.adapter.HistoricRequestToHistoricEntityAdapter;
import br.com.jesus.lojavirtual.controller.request.HistoricRequest;
import br.com.jesus.lojavirtual.controller.response.HistoricResponse;
import br.com.jesus.lojavirtual.controller.response.exception.HistoricCreateException;
import br.com.jesus.lojavirtual.controller.response.exception.HistoricNotFoundException;
import br.com.jesus.lojavirtual.domain.entity.mysql.HistoricEntity;
import br.com.jesus.lojavirtual.enumerated.MessageEnum;
import br.com.jesus.lojavirtual.repository.mysql.HistoricRepository;
import br.com.jesus.lojavirtual.service.HistoricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HistoricServiceImpl implements HistoricService {

    private final HistoricRepository historicRepository;
    private final HistoricRequestToHistoricEntityAdapter historicRequestToHistoricEntityAdapter;
    private final HistoricEntityToResponseAdapter historicEntityToResponseAdapter;

    @Autowired
    public HistoricServiceImpl(HistoricRepository historicRepository, HistoricRequestToHistoricEntityAdapter historicRequestToHistoricEntityAdapter, HistoricEntityToResponseAdapter historicEntityToResponseAdapter) {
        this.historicRepository = historicRepository;
        this.historicRequestToHistoricEntityAdapter = historicRequestToHistoricEntityAdapter;
        this.historicEntityToResponseAdapter = historicEntityToResponseAdapter;
    }

    @Override
    public HistoricResponse retrieveHistoric(Integer id) throws HistoricNotFoundException {
        HistoricEntity historicEntity =
                historicRepository.findById(id)
                        .orElseThrow(() -> new HistoricNotFoundException(MessageEnum.HISTORIC_NOT_FOUND_EXCEPTION.getValue()));

        return historicEntityToResponseAdapter.getHistoricResponse(historicEntity);
    }

    @Override
    public List<HistoricResponse> retrieveListHistorics() throws HistoricNotFoundException {

        List<HistoricEntity> historicEntityList =
                Optional.of(historicRepository.findAll())
                        .orElseThrow(() -> new HistoricNotFoundException(MessageEnum.HISTORIC_LIST_NOT_FOUND_EXCEPTION.getValue()));

        return historicEntityToResponseAdapter.buildListHistoricResponse(historicEntityList);
    }

    @Override
    public HistoricResponse createOrUpdateHistoric(HistoricRequest historicRequest) throws HistoricCreateException {
        historicRequest.setDate(LocalDate.now());
        HistoricEntity historicEntity = historicRequestToHistoricEntityAdapter.getHistoricEntity(historicRequest);

        HistoricEntity historicEntitySaved =
                Optional.of(this.historicRepository.save(historicEntity))
                        .orElseThrow(() -> new HistoricCreateException(MessageEnum.HISTORIC_ERROR_ON_CREATE_EXCEPTION.getValue()));

        return historicEntityToResponseAdapter.getHistoricResponse(historicEntitySaved);
    }

    @Override
    public void deleteHistoric(Integer id) {
        this.historicRepository.deleteById(id);
    }
}
