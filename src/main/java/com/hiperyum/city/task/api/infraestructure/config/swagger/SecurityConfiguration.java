package com.hiperyum.city.task.api.infraestructure.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll() // Permitir acceso a Swagger sin autenticación
                        .anyRequest().authenticated() // Requiere autenticación para otras rutas
                )
                .formLogin()  // Habilitar formulario de login
                .and()
                .httpBasic();  // Habilitar autenticación básica
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password("{noop}password")  // {noop} es para indicar que la contraseña está en texto claro
                        .roles("USER")
                        .build(),
                User.withUsername("admin")
                        .password("{noop}admin123")
                        .roles("ADMIN")
                        .build()
        );
    }
}
