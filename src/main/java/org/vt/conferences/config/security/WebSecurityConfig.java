package org.vt.conferences.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, WebEndpointProperties webEndpointProperties,
            SecurityProperties securityProperties) throws Exception {
        return http.authorizeHttpRequests(
                        requests -> requests.requestMatchers(webEndpointProperties.getBasePath() + "/health/**")
                                .permitAll()
                                .requestMatchers(webEndpointProperties.getBasePath() + "/**")
                                .hasRole("ADMIN")
                                .requestMatchers(webEndpointProperties.getBasePath() + "/prometheus")
                                .permitAll()
                                .requestMatchers("/" + applicationName + "/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .userDetailsService(this.actuatorUserDetailsService(securityProperties))
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService actuatorUserDetailsService(SecurityProperties securityProperties) {
        var user = securityProperties.getUser();
        var roles = user.getRoles()
                .toArray(String[]::new);
        UserDetails userMemory = User.builder()
                .username(user.getName())
                .password(this.passwordEncoder()
                        .encode(user.getPassword()))
                .roles(roles)
                .build();
        return new InMemoryUserDetailsManager(userMemory);
    }
}
