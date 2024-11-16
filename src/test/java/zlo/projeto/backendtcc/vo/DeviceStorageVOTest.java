package zlo.projeto.backendtcc.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeviceStorageVOTest {

    @Test
    void shouldSerializeToJson() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Adiciona suporte ao Instant

        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MILLIS); // Trunca para milissegundos
        DeviceStorageVO vo = new DeviceStorageVO(1, "token123", now);

        // Act
        String json = objectMapper.writeValueAsString(vo);

        // Assert
        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"tokenDispositivo\":\"token123\""));
        assertTrue(json.contains("\"dataCadastro\":\"" + now.toString() + "\""));
    }

    @Test
    void shouldDeserializeFromJson() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Adiciona suporte ao Instant

        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MILLIS); // Trunca para milissegundos
        String json = String.format("{\"id\":1,\"tokenDispositivo\":\"token123\",\"dataCadastro\":\"%s\"}", now.toString());

        // Act
        DeviceStorageVO vo = objectMapper.readValue(json, DeviceStorageVO.class);

        // Assert
        assertEquals(1, vo.getId());
        assertEquals("token123", vo.getTokenDispositivo());
        assertEquals(now, vo.getDataCadastro());
    }
}
