package zlo.projeto.backendtcc.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import zlo.projeto.backendtcc.dto.commands.DeviceStorageDTO;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeviceStorageDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDeviceStorageDTO() {
        DeviceStorageDTO dto = new DeviceStorageDTO();
        dto.setTokenDispositivo("validToken");
        dto.setCpfResponsavel("12345678901");

        Set<ConstraintViolation<DeviceStorageDTO>> violations = validator.validate(dto);
        assertEquals(0, violations.size(), "DTO should be valid");
    }

    @Test
    void testNullTokenDispositivo() {
        DeviceStorageDTO dto = new DeviceStorageDTO();
        dto.setTokenDispositivo(null);
        dto.setCpfResponsavel("12345678901");

        Set<ConstraintViolation<DeviceStorageDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "tokenDispositivo should not be null");
    }

    @Test
    void testTokenDispositivoExceedsMaxSize() {
        DeviceStorageDTO dto = new DeviceStorageDTO();
        dto.setTokenDispositivo("a".repeat(256)); // Exceeds max size of 255
        dto.setCpfResponsavel("12345678901");

        Set<ConstraintViolation<DeviceStorageDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "tokenDispositivo should have max size of 255");
    }

    @Test
    void testNullCpfResponsavel() {
        DeviceStorageDTO dto = new DeviceStorageDTO();
        dto.setTokenDispositivo("validToken");
        dto.setCpfResponsavel(null);

        Set<ConstraintViolation<DeviceStorageDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "cpfResponsavel should not be null");
    }
}

