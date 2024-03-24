package trkpo.spbstu.hospitalavailability.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import trkpo.spbstu.hospitalavailability.exception.ForbiddenException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SecurityUtilsTest {

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt jwt;

    @BeforeEach
    void setUp() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void shouldReturnUserKeyString() {
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getSubject()).thenReturn("UserKey");

        String userKey = SecurityUtils.getUserKey();
        assertEquals("UserKey", userKey);
    }

    @Test
    void shouldThrowForbiddenExceptionForInvalidUserKey() {
        when(authentication.getPrincipal()).thenThrow(getClassCastException());

        assertThrows(ForbiddenException.class, SecurityUtils::getUserKey);
    }

    @Test
    void shouldReturnUserMailString() {
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getClaimAsString("preferred_username")).thenReturn("UserMail");

        String userMail = SecurityUtils.getUserMail();
        assertEquals("UserMail", userMail);
    }

    @Test
    void shouldThrowForbiddenExceptionForInvalidUserMail() {
        when(authentication.getPrincipal()).thenThrow(getClassCastException());

        assertThrows(ForbiddenException.class, SecurityUtils::getUserMail);
    }

    private ClassCastException getClassCastException() {
        return new ClassCastException("Cannot cast to Jwt");
    }
}
