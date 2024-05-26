package br.com.ecommerce.domain.service.impl;

import br.com.ecommerce.adapter.toresponse.PaymentHistoricEntityToResponseAdapter;
import br.com.ecommerce.adapter.toentity.PaymentHistoricRequestToHistoricEntityAdapter;
import br.com.ecommerce.controller.request.PaymentHistoricRequest;
import br.com.ecommerce.controller.response.PaymentHistoricResponse;
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
    private final PaymentHistoricRequestToHistoricEntityAdapter paymentHistoricRequestToHistoricEntityAdapter;
    private final PaymentHistoricEntityToResponseAdapter paymentHistoricEntityToResponseAdapter;

    @Autowired
    public PaymentHistoricServiceImpl(PaymentHistoricRepository paymentHistoricRepository, PaymentHistoricRequestToHistoricEntityAdapter paymentHistoricRequestToHistoricEntityAdapter, PaymentHistoricEntityToResponseAdapter paymentHistoricEntityToResponseAdapter) {
        this.paymentHistoricRepository = paymentHistoricRepository;
        this.paymentHistoricRequestToHistoricEntityAdapter = paymentHistoricRequestToHistoricEntityAdapter;
        this.paymentHistoricEntityToResponseAdapter = paymentHistoricEntityToResponseAdapter;
    }

    @Override
    public PaymentHistoricResponse retrieveHistoric(Integer id) throws PaymentHistoricNotFoundException {
        PaymentHistoricEntity paymentHistoricEntity =
                paymentHistoricRepository.findById(id)
                        .orElseThrow(() -> new PaymentHistoricNotFoundException(MessageEnum.PAYMENT_HISTORIC_NOT_FOUND_EXCEPTION.getValue(), new Exception()));

        return paymentHistoricEntityToResponseAdapter.getHistoricResponse(paymentHistoricEntity);
    }

    @Override
    public List<PaymentHistoricResponse> retrieveListHistorics() throws PaymentHistoricNotFoundException {
        List<PaymentHistoricEntity> paymentHistoricEntityList = paymentHistoricRepository.findAll();
        if (paymentHistoricEntityList.isEmpty())
            throw new PaymentHistoricNotFoundException(MessageEnum.PAYMENT_HISTORIC_LIST_NOT_FOUND_EXCEPTION.getValue(), new Exception());

        return paymentHistoricEntityToResponseAdapter.buildListHistoricResponse(paymentHistoricEntityList);
    }

    @Override
    @Transactional
    public PaymentHistoricResponse createHistoric(PaymentHistoricRequest paymentHistoricRequest) throws PaymentHistoricCreateException {
        paymentHistoricRequest.setDate(LocalDate.now());
        PaymentHistoricEntity paymentHistoricEntity = paymentHistoricRequestToHistoricEntityAdapter.getHistoricEntity(paymentHistoricRequest);

        try {
            PaymentHistoricEntity paymentHistoricEntitySaved = this.paymentHistoricRepository.save(paymentHistoricEntity);
            return paymentHistoricEntityToResponseAdapter.getHistoricResponse(paymentHistoricEntitySaved);
        } catch (Exception exception) {
            throw new PaymentHistoricCreateException(MessageEnum.PAYMENT_HISTORIC_ERROR_ON_CREATE_EXCEPTION.getValue(), exception);
        }
    }

    @Override
    @Transactional
    public void deleteHistoric(Integer id) throws PaymentHistoricDeleteException {
        try {
            this.paymentHistoricRepository.deleteById(id);
        } catch (Exception exception) {
            throw new PaymentHistoricDeleteException(MessageEnum.PAYMENT_HISTORIC_ERROR_ON_DELETE_EXCEPTION.getValue(), exception);
        }
    }
}
