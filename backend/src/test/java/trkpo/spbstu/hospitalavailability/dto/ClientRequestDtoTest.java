package trkpo.spbstu.hospitalavailability.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClientRequestDtoTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final String ID = "id";
    private final String EMAIL = "test@domain.com";

    @Test
    void testKeycloakIdIsNull() {
        ClientRequestDto clientDto = new ClientRequestDto();
        clientDto.setEmail(EMAIL);
        Set<ConstraintViolation<ClientRequestDto>> violations = this.validator.validate(clientDto);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void testEmailIsNull() {
        ClientRequestDto clientDto = new ClientRequestDto();
        clientDto.setKeycloakId(ID);

        Set<ConstraintViolation<ClientRequestDto>> violations = this.validator.validate(clientDto);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void testEmailIsInvalid() {
        ClientRequestDto clientDto = new ClientRequestDto();
        clientDto.setKeycloakId(ID);
        clientDto.setEmail("invalidEmail");

        Set<ConstraintViolation<ClientRequestDto>> violations = this.validator.validate(clientDto);
        assertEquals(1, violations.size());
        assertEquals("must be a well-formed email address", violations.iterator().next().getMessage());
    }

    @Test
    void testValidClientRequestDto() {
        ClientRequestDto clientDto = new ClientRequestDto();
        clientDto.setKeycloakId(ID);
        clientDto.setEmail(EMAIL);

        Set<ConstraintViolation<ClientRequestDto>> violations = this.validator.validate(clientDto);
        assertTrue(violations.isEmpty());
        assertEquals(clientDto.getKeycloakId(), ID);
        assertEquals(clientDto.getEmail(), EMAIL);
    }
}
