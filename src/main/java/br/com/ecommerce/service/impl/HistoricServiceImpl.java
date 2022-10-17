package br.com.ecommerce.service.impl;

import br.com.ecommerce.adapter.HistoricEntityToResponseAdapter;
import br.com.ecommerce.adapter.HistoricRequestToHistoricEntityAdapter;
import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.controller.response.HistoricResponse;
import br.com.ecommerce.controller.response.exception.HistoricCreateException;
import br.com.ecommerce.controller.response.exception.HistoricDeleteException;
import br.com.ecommerce.controller.response.exception.HistoricNotFoundException;
import br.com.ecommerce.domain.entity.mysql.HistoricEntity;
import br.com.ecommerce.domain.repository.mysql.HistoricRepository;
import br.com.ecommerce.controller.response.enumerated.MessageEnum;
import br.com.ecommerce.service.HistoricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
                        .orElseThrow(() -> new HistoricNotFoundException(MessageEnum.HISTORIC_NOT_FOUND_EXCEPTION.getValue(), new Exception()));

        return historicEntityToResponseAdapter.getHistoricResponse(historicEntity);
    }

    @Override
    public List<HistoricResponse> retrieveListHistorics() throws HistoricNotFoundException {
        List<HistoricEntity> historicEntityList = historicRepository.findAll();
        if (historicEntityList.isEmpty())
            throw new HistoricNotFoundException(MessageEnum.HISTORIC_LIST_NOT_FOUND_EXCEPTION.getValue(), new Exception());

        return historicEntityToResponseAdapter.buildListHistoricResponse(historicEntityList);
    }

    @Override
    public HistoricResponse createOrUpdateHistoric(HistoricRequest historicRequest) throws HistoricCreateException {
        historicRequest.setDate(LocalDate.now());
        HistoricEntity historicEntity = historicRequestToHistoricEntityAdapter.getHistoricEntity(historicRequest);

        try {
            HistoricEntity historicEntitySaved = this.historicRepository.save(historicEntity);
            return historicEntityToResponseAdapter.getHistoricResponse(historicEntitySaved);
        } catch (Exception exception) {
            throw new HistoricCreateException(MessageEnum.HISTORIC_ERROR_ON_CREATE_EXCEPTION.getValue(), exception);
        }
    }

    @Override
    public void deleteHistoric(Integer id) throws HistoricDeleteException {
        try {
            this.historicRepository.deleteById(id);
        } catch (Exception exception) {
            throw new HistoricDeleteException(MessageEnum.HISTORIC_ERROR_ON_DELETE_EXCEPTION.getValue(), exception);
        }
    }
}
