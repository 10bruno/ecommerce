package br.com.jesus.lojavirtual.util;

import br.com.jesus.lojavirtual.mysql.domain.TipoPagamento;

public class TestConstants {
    public static final Integer id1 = 1;
    public static final Integer id2 = 2;
    public static final String descricaoTotal = "pagamento total";
    public static final String descricaoParcial = "pagamento parcial";
    public static final String tipoDebito = TipoPagamento.DEBITO.name();
    public static final String tipoCredito = TipoPagamento.CREDITO.name();

}
