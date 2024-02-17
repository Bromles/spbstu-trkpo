package trkpo.spbstu.hospitalavailability.dto;

import lombok.Cleanup;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientRequestDtoTest {
    private Validator validator;

    @Before
    public void setUp() {
        @Cleanup
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testKeycloakIdIsNull() {
        ClientRequestDto clientDto = new ClientRequestDto();
        clientDto.setEmail("test@domain.com");
        Set<ConstraintViolation<ClientRequestDto>> violations = this.validator.validate(clientDto);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void testEmailIsNull() {
        ClientRequestDto clientDto = new ClientRequestDto();
        clientDto.setKeycloakId("id");

        Set<ConstraintViolation<ClientRequestDto>> violations = this.validator.validate(clientDto);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmailIsInvalid() {
        ClientRequestDto clientDto = new ClientRequestDto();
        clientDto.setKeycloakId("id");
        clientDto.setEmail("invalidEmail");

        Set<ConstraintViolation<ClientRequestDto>> violations = this.validator.validate(clientDto);
        assertEquals(1, violations.size());
        assertEquals("must be a well-formed email address", violations.iterator().next().getMessage());
    }

    @Test
    public void testValidClientRequestDto() {
        ClientRequestDto clientDto = new ClientRequestDto();
        String id = "id";
        String email = "test@domain.com";
        clientDto.setKeycloakId(id);
        clientDto.setEmail(email);

        Set<ConstraintViolation<ClientRequestDto>> violations = this.validator.validate(clientDto);
        assertTrue(violations.isEmpty());
        assertEquals(clientDto.getEmail(), email);
        assertEquals(clientDto.getKeycloakId(), id);
    }
}
