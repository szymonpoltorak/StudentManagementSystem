package razepl.dev.sms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import razepl.dev.sms.config.interfaces.SecurityConfigInterface;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements SecurityConfigInterface {
    @Bean
    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                );
        return httpSecurity.build();
    }
}
