package br.com.ecommerce.domain.service.impl;

import br.com.ecommerce.adapter.toresponse.HistoricEntityToResponseAdapter;
import br.com.ecommerce.adapter.toentity.HistoricRequestToHistoricEntityAdapter;
import br.com.ecommerce.controller.request.HistoricRequest;
import br.com.ecommerce.controller.response.HistoricResponse;
import br.com.ecommerce.domain.service.PaymentHistoricService;
import br.com.ecommerce.infra.exception.PaymentHistoricCreateException;
import br.com.ecommerce.infra.exception.PaymentHistoricDeleteException;
import br.com.ecommerce.infra.exception.PaymentHistoricNotFoundException;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import br.com.ecommerce.domain.repository.mysql.PaymentHistoricRepository;
import br.com.ecommerce.controller.common.enumerated.MessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentHistoricServiceImpl implements PaymentHistoricService {

    private final PaymentHistoricRepository paymentHistoricRepository;
    private final HistoricRequestToHistoricEntityAdapter historicRequestToHistoricEntityAdapter;
    private final HistoricEntityToResponseAdapter historicEntityToResponseAdapter;

    @Autowired
    public PaymentHistoricServiceImpl(PaymentHistoricRepository paymentHistoricRepository, HistoricRequestToHistoricEntityAdapter historicRequestToHistoricEntityAdapter, HistoricEntityToResponseAdapter historicEntityToResponseAdapter) {
        this.paymentHistoricRepository = paymentHistoricRepository;
        this.historicRequestToHistoricEntityAdapter = historicRequestToHistoricEntityAdapter;
        this.historicEntityToResponseAdapter = historicEntityToResponseAdapter;
    }

    @Override
    public HistoricResponse retrieveHistoric(Integer id) throws PaymentHistoricNotFoundException {
        PaymentHistoricEntity paymentHistoricEntity =
                paymentHistoricRepository.findById(id)
                        .orElseThrow(() -> new PaymentHistoricNotFoundException(MessageEnum.HISTORIC_NOT_FOUND_EXCEPTION.getValue(), new Exception()));

        return historicEntityToResponseAdapter.getHistoricResponse(paymentHistoricEntity);
    }

    @Override
    public List<HistoricResponse> retrieveListHistorics() throws PaymentHistoricNotFoundException {
        List<PaymentHistoricEntity> paymentHistoricEntityList = paymentHistoricRepository.findAll();
        if (paymentHistoricEntityList.isEmpty())
            throw new PaymentHistoricNotFoundException(MessageEnum.HISTORIC_LIST_NOT_FOUND_EXCEPTION.getValue(), new Exception());

        return historicEntityToResponseAdapter.buildListHistoricResponse(paymentHistoricEntityList);
    }

    @Override
    @Transactional
    public HistoricResponse createHistoric(HistoricRequest historicRequest) throws PaymentHistoricCreateException {
        historicRequest.setDate(LocalDate.now());
        PaymentHistoricEntity paymentHistoricEntity = historicRequestToHistoricEntityAdapter.getHistoricEntity(historicRequest);

        try {
            PaymentHistoricEntity paymentHistoricEntitySaved = this.paymentHistoricRepository.save(paymentHistoricEntity);
            return historicEntityToResponseAdapter.getHistoricResponse(paymentHistoricEntitySaved);
        } catch (Exception exception) {
            throw new PaymentHistoricCreateException(MessageEnum.HISTORIC_ERROR_ON_CREATE_EXCEPTION.getValue(), exception);
        }
    }

    @Override
    @Transactional
    public void deleteHistoric(Integer id) throws PaymentHistoricDeleteException {
        try {
            this.paymentHistoricRepository.deleteById(id);
        } catch (Exception exception) {
            throw new PaymentHistoricDeleteException(MessageEnum.HISTORIC_ERROR_ON_DELETE_EXCEPTION.getValue(), exception);
        }
    }
}
