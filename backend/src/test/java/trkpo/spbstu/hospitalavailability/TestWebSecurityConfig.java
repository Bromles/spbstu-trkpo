package trkpo.spbstu.hospitalavailability;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@Profile("test")
class TestWebSecurityConfig {

    @Bean
    @Primary
    public SecurityFilterChain  testSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorizeRequests -> authorizeRequests
                .anyRequest().permitAll()
        );

        return http.build();
    }

}

