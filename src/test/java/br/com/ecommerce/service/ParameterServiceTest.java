package br.com.ecommerce.service;

import br.com.ecommerce.config.ParameterConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParameterServiceTest {

    @InjectMocks
    private ParameterService parameterService;

    @Mock
    private ParameterConfig parameterConfig;

    @Test
    void shouldCreateParameterService_whenParameterConfigProvided() {
        assertNotNull(parameterService);
    }

    @Test
    void shouldGetSequenceParameter_whenParameterConfigHasSequence() {
        String[] testSequence = {"value1", "value2", "value3"};
        String[] valueSequence = {"val1", "val2"};
        
        when(parameterConfig.getSequence()).thenReturn(testSequence);
        ReflectionTestUtils.setField(parameterService, "sequence", valueSequence);

        assertDoesNotThrow(() -> parameterService.getSequenceParameter());
        
        verify(parameterConfig, times(1)).getSequence();
    }

    @Test
    void shouldGetSequenceParameter_whenParameterConfigSequenceIsNull() {
        when(parameterConfig.getSequence()).thenReturn(null);
        ReflectionTestUtils.setField(parameterService, "sequence", null);

        assertThrows(NullPointerException.class, () -> parameterService.getSequenceParameter());
        
        verify(parameterConfig, times(1)).getSequence();
    }

    @Test
    void shouldGetSequenceParameter_whenParameterConfigSequenceIsEmpty() {
        String[] emptySequence = {};
        String[] valueSequence = {};
        
        when(parameterConfig.getSequence()).thenReturn(emptySequence);
        ReflectionTestUtils.setField(parameterService, "sequence", valueSequence);

        assertDoesNotThrow(() -> parameterService.getSequenceParameter());
        
        verify(parameterConfig, times(1)).getSequence();
    }

    @Test
    void shouldGetListParameter_whenParameterConfigHasList() {
        List<String> testList = List.of("item1", "item2", "item3");
        List<String> valueList = List.of("listItem1", "listItem2");
        
        when(parameterConfig.getList()).thenReturn(testList);
        ReflectionTestUtils.setField(parameterService, "list", valueList);

        assertDoesNotThrow(() -> parameterService.getListParameter());
        
        verify(parameterConfig, times(1)).getList();
    }

    @Test
    void shouldGetListParameter_whenParameterConfigListIsNull() {
        when(parameterConfig.getList()).thenReturn(null);
        ReflectionTestUtils.setField(parameterService, "list", null);

        assertDoesNotThrow(() -> parameterService.getListParameter());
        
        verify(parameterConfig, times(1)).getList();
    }

    @Test
    void shouldGetListParameter_whenParameterConfigListIsEmpty() {
        List<String> emptyList = List.of();
        List<String> valueList = List.of();
        
        when(parameterConfig.getList()).thenReturn(emptyList);
        ReflectionTestUtils.setField(parameterService, "list", valueList);

        assertDoesNotThrow(() -> parameterService.getListParameter());
        
        verify(parameterConfig, times(1)).getList();
    }

    @Test
    void shouldHandleMixedScenarios_whenCallingBothMethods() {
        String[] testSequence = {"seq1", "seq2"};
        List<String> testList = List.of("list1", "list2");
        
        when(parameterConfig.getSequence()).thenReturn(testSequence);
        when(parameterConfig.getList()).thenReturn(testList);
        
        ReflectionTestUtils.setField(parameterService, "sequence", new String[]{"val1"});
        ReflectionTestUtils.setField(parameterService, "list", List.of("listVal1"));

        assertDoesNotThrow(() -> {
            parameterService.getSequenceParameter();
            parameterService.getListParameter();
        });
        
        verify(parameterConfig, times(1)).getSequence();
        verify(parameterConfig, times(1)).getList();
    }

    @Test
    void shouldVerifyFieldsAreInjected_whenServiceIsCreated() {
        assertNotNull(ReflectionTestUtils.getField(parameterService, "parameterConfig"));
        // sequence and list fields are injected by Spring @Value, so they might be null in unit tests
        // but the fields should exist
        assertDoesNotThrow(() -> ReflectionTestUtils.getField(parameterService, "sequence"));
        assertDoesNotThrow(() -> ReflectionTestUtils.getField(parameterService, "list"));
    }
}