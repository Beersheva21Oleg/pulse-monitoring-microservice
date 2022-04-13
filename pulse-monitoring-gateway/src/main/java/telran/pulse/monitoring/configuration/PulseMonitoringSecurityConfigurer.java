package telran.pulse.monitoring.configuration;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class PulseMonitoringSecurityConfigurer {

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityWebFilterChain getSecurityWebFilterChain(ServerHttpSecurity http) {
        http.httpBasic();
        http.cors().and().csrf().disable();
        http.authorizeExchange().pathMatchers("/login").permitAll();
        http.authorizeExchange().pathMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN");
        http.authorizeExchange().anyExchange().hasAnyRole("ADMIN");
        return http.build();
    }

}
