package br.com.ecommerce.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ParameterConfigTest {

    @Test
    void shouldCreateParameterConfigWithBuilder_whenAllFieldsProvided() {
        String[] sequence = {"value1", "value2"};
        List<String> list = List.of("item1", "item2");

        ParameterConfig config = ParameterConfig.builder()
                .sequence(sequence)
                .list(list)
                .build();

        assertNotNull(config);
        assertArrayEquals(sequence, config.getSequence());
        assertEquals(list, config.getList());
    }

    @Test
    void shouldCreateParameterConfigWithAllArgsConstructor_whenAllFieldsProvided() {
        String[] sequence = {"value1", "value2"};
        List<String> list = List.of("item1", "item2");

        ParameterConfig config = new ParameterConfig(sequence, list);

        assertNotNull(config);
        assertArrayEquals(sequence, config.getSequence());
        assertEquals(list, config.getList());
    }

    @Test
    void shouldCreateParameterConfigWithNoArgsConstructor_whenNoFieldsProvided() {
        ParameterConfig config = new ParameterConfig();

        assertNotNull(config);
        assertNull(config.getSequence());
        assertNull(config.getList());
    }

    @Test
    void shouldSetAndGetSequence_whenValidArray() {
        ParameterConfig config = new ParameterConfig();
        String[] sequence = {"test1", "test2"};

        config.setSequence(sequence);

        assertArrayEquals(sequence, config.getSequence());
    }

    @Test
    void shouldSetAndGetList_whenValidList() {
        ParameterConfig config = new ParameterConfig();
        List<String> list = List.of("test1", "test2");

        config.setList(list);

        assertEquals(list, config.getList());
    }

    @Test
    void shouldHandleNullValues_whenSettingFields() {
        ParameterConfig config = new ParameterConfig();
        
        config.setSequence(null);
        config.setList(null);

        assertNull(config.getSequence());
        assertNull(config.getList());
    }

    @Test
    void shouldImplementToString_whenCalled() {
        String[] sequence = {"value1"};
        List<String> list = List.of("item1");
        ParameterConfig config = new ParameterConfig(sequence, list);

        String result = config.toString();

        assertNotNull(result);
        assertTrue(result.contains("ParameterConfig"));
    }

    @Test
    void shouldImplementEqualsAndHashCode_whenComparingObjects() {
        String[] sequence = {"value1"};
        List<String> list = List.of("item1");
        
        ParameterConfig config1 = new ParameterConfig(sequence, list);
        ParameterConfig config2 = new ParameterConfig(sequence, list);

        assertEquals(config1, config2);
        assertEquals(config1.hashCode(), config2.hashCode());
    }
}