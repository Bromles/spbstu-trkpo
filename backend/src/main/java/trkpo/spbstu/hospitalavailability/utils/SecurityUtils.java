package trkpo.spbstu.hospitalavailability.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import trkpo.spbstu.hospitalavailability.exception.ForbiddenException;

@UtilityClass
public class SecurityUtils {
    public String getUserKey() {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return jwt.getSubject();
        } catch (ClassCastException e) {
            throw new ForbiddenException("Cannot find JWT", e);
        }
    }

    public String getUserMail() {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return jwt.getClaimAsString("preferred_username");
        } catch (ClassCastException e) {
            throw new ForbiddenException("Cannot find JWT", e);
        }
    }
}
