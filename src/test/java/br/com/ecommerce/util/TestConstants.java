package br.com.ecommerce.util;

import br.com.ecommerce.domain.enumerated.PaymentType;

public class TestConstants {
    public static final Integer ID_1 = 1;
    public static final Integer ID_2 = 2;
    public static final String FULL_DESCRIPTION = "Total payment";
    public static final String PARTIAL_DESCRIPTION = "Partial payment";
    public static final String DEBIT_TYPE = PaymentType.DEBIT.name();
    public static final String CREDIT_TYPE = PaymentType.CREDIT.name();

}
