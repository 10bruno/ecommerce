package br.com.jesus.lojavirtual.util;

import br.com.jesus.lojavirtual.domain.entity.mysql.Historico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockBuilders {

    public static Historico buildHistorico() {
        Historico historico = new Historico();
        historico.setId(TestConstants.id1);
        historico.setTipo(TestConstants.tipoDebito);
        historico.setData(LocalDate.now());
        historico.setDescricao(TestConstants.descricaoTotal);
        return historico;
    }

    public static Historico buildHistorico2() {
        Historico historico = new Historico();
        historico.setId(TestConstants.id2);
        historico.setTipo(TestConstants.tipoCredito);
        historico.setData(LocalDate.now());
        historico.setDescricao(TestConstants.descricaoTotal);
        return historico;
    }

    public static List<Historico> buildListHistorico() {
        Historico historico1 = MockBuilders.buildHistorico();
        Historico historico2 = MockBuilders.buildHistorico2();
        List<Historico> historicoList = new ArrayList<>();
        historicoList.add(historico1);
        historicoList.add(historico2);
        return historicoList;
    }

}
