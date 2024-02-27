package trkpo.spbstu.hospitalavailability.config;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jwt.JWTClaimsSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.CorsFilter;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WebSecurityConfigTest {

    private final WebSecurityConfig webSecurityConfig = new WebSecurityConfig();


    @Test
    void testCorsFilter() {
        CorsFilter result = webSecurityConfig.cors();

        assertThat(result).isNotNull();
    }

    @Test
    void testJwtAuthenticationConverter() {
        final Converter<Jwt, AbstractAuthenticationToken> result = webSecurityConfig.jwtAuthenticationConverter();

        assertThat(result).isNotNull();
    }

    @Test
    void testJwtGrantedAuthoritiesConverter() {
        JSONObject realmAccess = new JSONObject();
        JSONArray arr = new JSONArray();
        arr.add("admin");
        arr.add("user");
        realmAccess.put("roles", arr);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().claim("realm_access", realmAccess).build();

        Jwt jwt = Jwt.withTokenValue("testToken").header("alg", "none").claims(claims -> claims.putAll(jwtClaimsSet.getClaims())).build();

        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();

        Collection<GrantedAuthority> grantedAuthorities = webSecurityConfig.jwtGrantedAuthoritiesConverter().convert(jwt);

        assertThat(grantedAuthorities).hasSize(2);
        assertThat(grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).containsExactlyInAnyOrder("ROLE_ADMIN", "ROLE_USER");
    }

    @Test
    void testJwtGrantedAuthoritiesConverterWhenNoRealmAccess() {
        JSONObject realmAccess = new JSONObject();
        JSONArray arr = new JSONArray();
        arr.add("admin");
        arr.add("user");
        realmAccess.put("roles", arr);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().claim("no_access", realmAccess).build();

        Jwt jwt = Jwt.withTokenValue("testToken").header("alg", "none").claims(claims -> claims.putAll(jwtClaimsSet.getClaims())).build();

        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();

        Collection<GrantedAuthority> grantedAuthorities = webSecurityConfig.jwtGrantedAuthoritiesConverter().convert(jwt);

        assertThat(grantedAuthorities).hasSize(0);
    }

    @Test
    void testJwtGrantedAuthoritiesConverterWhenNoRoles() {
        JSONObject realmAccess = new JSONObject();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().claim("realm_access", realmAccess).build();

        Jwt jwt = Jwt.withTokenValue("testToken").header("alg", "none").claims(claims -> claims.putAll(jwtClaimsSet.getClaims())).build();

        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();

        Collection<GrantedAuthority> grantedAuthorities = webSecurityConfig.jwtGrantedAuthoritiesConverter().convert(jwt);

        assertThat(grantedAuthorities).hasSize(0);
    }

}

