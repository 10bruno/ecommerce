package br.com.jesus.lojavirtual.controller;

import br.com.jesus.lojavirtual.mysql.domain.Historico;
import br.com.jesus.lojavirtual.service.HistoricoService;
import br.com.jesus.lojavirtual.util.MockBuilders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoricoControllerTest {

    @InjectMocks
    private HistoricoController historicoController;

    @Mock
    private HistoricoService historicoService;

    @Test
    public void deveRecuperarUmHistoricoDoBanco() {
        Historico historico = MockBuilders.buildHistorico();
        when(this.historicoService.recuperaUmHistorico(historico.getId())).thenReturn(Optional.of(historico));

        Optional<Historico> historicoReturn = this.historicoController.getUmHistorico(historico.getId());

        Assertions.assertEquals(Optional.of(historico), historicoReturn);
    }

    @Test
    public void deveRecuperarUmaListaDeHistoricosDoBanco() {
        List<Historico> historicoList = MockBuilders.buildListHistorico();
        when(this.historicoService.recuperaListaHistoricos()).thenReturn(historicoList);

        List<Historico> historicoListReturn = this.historicoController.getListaHistoricos();

        Assertions.assertEquals(historicoList, historicoListReturn);
        Assertions.assertEquals(2, historicoList.size());
    }

    @Test
    public void deveAlterarUmElementoDeUmHistoricoNoBanco() {
        Historico historico = MockBuilders.buildHistorico();
        when(this.historicoService.criaAtualizaHistorico(historico)).thenReturn(historico);

        Historico historicoReturn = this.historicoController.putUmHistorico(historico);

        Assertions.assertEquals(historico, historicoReturn);
    }

    @Test
    public void deveCriarUmNovoHistoricoNoBanco() {
        Historico historico = MockBuilders.buildHistorico();
        when(this.historicoService.criaAtualizaHistorico(historico)).thenReturn(historico);

        Historico historicoReturn = this.historicoController.postNovoHistorico(historico);

        Assertions.assertEquals(historico, historicoReturn);
    }

    @Test
    public void deveDeletarUmHistoricoNoBanco() {
        Historico historico = MockBuilders.buildHistorico();
        doNothing().when(this.historicoService).deletaHistorico(historico.getId());

        this.historicoController.deleteUmHistorico(historico.getId());

        verify(this.historicoService, times(1)).deletaHistorico(historico.getId());
    }

}
