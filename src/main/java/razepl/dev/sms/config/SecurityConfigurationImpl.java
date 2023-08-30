package razepl.dev.sms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import razepl.dev.sms.config.interfaces.SecurityConfiguration;
import razepl.dev.sms.config.jwt.interfaces.JwtAuthenticationFilter;
import razepl.dev.sms.exceptions.SecurityChainException;

import static razepl.dev.sms.config.constants.Matchers.LOGOUT_URL;
import static razepl.dev.sms.config.constants.Matchers.WHITE_LIST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurationImpl implements SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(request -> request
                            .requestMatchers(WHITE_LIST)
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                    )
                    .sessionManagement(
                            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .logout(logout -> logout
                            .logoutUrl(LOGOUT_URL)
                            .addLogoutHandler(logoutHandler)
                            .logoutSuccessHandler(
                                    (request, response, authentication) -> SecurityContextHolder.clearContext()
                            )
                    );
            return httpSecurity.build();
        } catch (Exception exception) {
            throw new SecurityChainException("Security chain has come with an error!");
        }
    }
}
