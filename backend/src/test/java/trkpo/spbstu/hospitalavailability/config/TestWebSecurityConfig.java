package trkpo.spbstu.hospitalavailability.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class TestWebSecurityConfig {
    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        webSecurityConfig.jwtAuthenticationConverter();

        http.authorizeRequests(authorizeRequests -> authorizeRequests
                .anyRequest().permitAll()
        );

        return http.build();
    }
}
