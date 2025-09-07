package br.com.ecommerce.util;

import br.com.ecommerce.controller.request.CustomerRequest;
import br.com.ecommerce.controller.request.InventoryRequest;
import br.com.ecommerce.controller.request.PaymentHistoricRequest;
import br.com.ecommerce.controller.request.ProductRequest;
import br.com.ecommerce.controller.response.CustomerResponse;
import br.com.ecommerce.controller.response.InventoryResponse;
import br.com.ecommerce.controller.response.PaymentHistoricResponse;
import br.com.ecommerce.controller.response.ProductResponse;
import br.com.ecommerce.domain.entity.mysql.PaymentHistoricEntity;
import br.com.ecommerce.domain.entity.postgres.CustomerEntity;
import br.com.ecommerce.domain.entity.postgres.InventoryEntity;
import br.com.ecommerce.domain.entity.postgres.ProductEntity;

import java.math.BigDecimal;
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

    public static PaymentHistoricRequest buildPaymentHistoricRequest() {
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

    public static List<CustomerResponse> buildListCustomerResponse() {
        return List.of(getCustomerResponse(), getCustomerSecondResponse());
    }

    public static CustomerResponse getCustomerSecondResponse() {
        return CustomerResponse
                .builder()
                .cpf("12345678910")
                .name("Maria")
                .birthDate("10101990")
                .gender("F")
                .build();
    }

    public static CustomerRequest buildCustomerRequest() {
        return CustomerRequest
                .builder()
                .cpf(TestConstants.CPF)
                .name(TestConstants.NAME)
                .birthDate(TestConstants.BIRTHDATE)
                .gender(TestConstants.GENDER)
                .build();
    }

    public static ProductResponse getProductResponse() {
        return ProductResponse
                .builder()
                .code(TestConstants.PRODUCT_CODE)
                .category(TestConstants.PRODUCT_CATEGORY)
                .title(TestConstants.PRODUCT_TITLE)
                .description(TestConstants.PRODUCT_DESCRIPTION)
                .weight(new BigDecimal(TestConstants.PRODUCT_WEIGHT))
                .dateRegister(LocalDate.now())
                .build();
    }

    public static ProductResponse getProductSecondResponse() {
        return ProductResponse
                .builder()
                .code("CD5678")
                .category("Books")
                .title("Test Book")
                .description("Test Book Description")
                .weight(new BigDecimal("0.50"))
                .dateRegister(LocalDate.now())
                .build();
    }

    public static List<ProductResponse> buildListProductResponse() {
        return List.of(getProductResponse(), getProductSecondResponse());
    }

    public static ProductRequest buildProductRequest() {
        return ProductRequest
                .builder()
                .code(TestConstants.PRODUCT_CODE)
                .category(TestConstants.PRODUCT_CATEGORY)
                .title(TestConstants.PRODUCT_TITLE)
                .description(TestConstants.PRODUCT_DESCRIPTION)
                .weight(new BigDecimal(TestConstants.PRODUCT_WEIGHT))
                .dateRegister(LocalDate.now())
                .build();
    }

    public static InventoryEntity getInventoryEntity() {
        return new InventoryEntity(TestConstants.ID_1, new BigDecimal(TestConstants.INVENTORY_AVAILABLE_QUANTITY), new BigDecimal(TestConstants.INVENTORY_RESERVED_QUANTITY), TestConstants.PRODUCT_CODE);
    }

    public static ProductEntity getProductEntity() {
        return new ProductEntity(TestConstants.PRODUCT_CODE, TestConstants.PRODUCT_CATEGORY, TestConstants.PRODUCT_TITLE, TestConstants.PRODUCT_DESCRIPTION, new BigDecimal(TestConstants.PRODUCT_WEIGHT), LocalDate.now());
    }

    public static InventoryResponse getInventoryResponse() {
        return InventoryResponse
                .builder()
                .id(TestConstants.ID_1)
                .availableQuantity(new BigDecimal(TestConstants.INVENTORY_AVAILABLE_QUANTITY))
                .reservedQuantity(new BigDecimal(TestConstants.INVENTORY_RESERVED_QUANTITY))
                .productCode(TestConstants.PRODUCT_CODE)
                .build();
    }

    public static InventoryResponse getInventorySecondResponse() {
        return InventoryResponse
                .builder()
                .id(TestConstants.ID_2)
                .availableQuantity(new BigDecimal("50"))
                .reservedQuantity(new BigDecimal("5"))
                .productCode("CD5678")
                .build();
    }

    public static List<InventoryResponse> buildListInventoryResponse() {
        return List.of(getInventoryResponse(), getInventorySecondResponse());
    }

    public static InventoryRequest buildInventoryRequest() {
        return InventoryRequest
                .builder()
                .id(TestConstants.ID_1)
                .availableQuantity(new BigDecimal(TestConstants.INVENTORY_AVAILABLE_QUANTITY))
                .reservedQuantity(new BigDecimal(TestConstants.INVENTORY_RESERVED_QUANTITY))
                .productCode(TestConstants.PRODUCT_CODE)
                .build();
    }

}
