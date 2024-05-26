package br.com.ecommerce.util;

import br.com.ecommerce.controller.request.PaymentHistoricRequest;
import br.com.ecommerce.controller.response.CustomerResponse;
import br.com.ecommerce.controller.response.PaymentHistoricResponse;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import br.com.ecommerce.domain.entity.postgres.CustomerEntity;

import java.time.LocalDate;
import java.util.List;

public class MockBuilders {

    public static PaymentHistoricEntity buildHistoric() {
        return new PaymentHistoricEntity(TestConstants.ID_1, TestConstants.FULL_DESCRIPTION, TestConstants.DEBIT_TYPE, LocalDate.now());
    }

    public static PaymentHistoricResponse getHistoricResponseFirst() {
        return PaymentHistoricResponse.builder()
                .id(TestConstants.ID_1)
                .type(TestConstants.DEBIT_TYPE)
                .description(TestConstants.FULL_DESCRIPTION)
                .date(LocalDate.now())
                .build();
    }

    public static PaymentHistoricResponse getHistoricResponseSecond() {
        return PaymentHistoricResponse.builder()
                .id(TestConstants.ID_2)
                .type(TestConstants.CREDIT_TYPE)
                .description(TestConstants.FULL_DESCRIPTION)
                .date(LocalDate.now())
                .build();
    }

    public static List<PaymentHistoricResponse> buildListHistoricResponse() {
        return List.of(getHistoricResponseFirst(), getHistoricResponseSecond());
    }

    public static PaymentHistoricRequest buildHistoricRequest() {
        return PaymentHistoricRequest
                .builder()
                .id(TestConstants.ID_1)
                .type(TestConstants.DEBIT_TYPE)
                .description(TestConstants.PARTIAL_DESCRIPTION)
                .date(LocalDate.now())
                .build();
    }

    public static CustomerEntity getCustomerEntity() {
        return new CustomerEntity(TestConstants.CPF, TestConstants.NAME, TestConstants.BIRTHDATE, TestConstants.GENDER);
    }

    public static CustomerResponse getCustomerResponse() {
        return CustomerResponse
                .builder()
                .cpf(TestConstants.CPF)
                .name(TestConstants.NAME)
                .birthDate(TestConstants.BIRTHDATE)
                .gender(TestConstants.GENDER)
                .build();
    }

}
