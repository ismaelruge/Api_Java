package com.ismaelruge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para pruebas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/publico/**").permitAll() // Permitir acceso a login y recursos estáticos
                        .requestMatchers("/privado/**").authenticated() // Proteger rutas privadas
                        .anyRequest().permitAll() // Permitir todo para interactuar con el saludo
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // Manejo de sesiones
                .formLogin(form -> form
                        .defaultSuccessUrl("/privado", true) // Redirige a "/privado" después de login exitoso
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login") // Redirige después del logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .httpBasic(basic -> {});

        return http.build();
    }
}
