package trkpo.spbstu.hospitalavailability.config;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                                authorizeRequests.anyRequest().authenticated()
                )
                .oauth2ResourceServer(resourceServerConfigure ->
                        resourceServerConfigure
                                .jwt(jwtConfigure ->
                                        jwtConfigure
                                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                )
                );

        return http.build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());

        return jwtAuthenticationConverter;
    }

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter delegate = new JwtGrantedAuthoritiesConverter();

        return (Jwt jwt) -> {
            Collection<GrantedAuthority> grantedAuthorities = delegate.convert(jwt);

            if (jwt.getClaims().get("realm_access") == null) {
                return grantedAuthorities;
            }

            JSONObject realmAccess = (JSONObject) jwt.getClaims().get("realm_access");
            if (realmAccess.get("roles") == null) {
                return grantedAuthorities;
            }

            JSONArray roles = (JSONArray) realmAccess.get("roles");
            List<SimpleGrantedAuthority> keycloakAuthorities = roles
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + ((String) role).toUpperCase()))
                    .collect(Collectors.toList());

            grantedAuthorities.addAll(keycloakAuthorities);
            return grantedAuthorities;
        };
    }
}