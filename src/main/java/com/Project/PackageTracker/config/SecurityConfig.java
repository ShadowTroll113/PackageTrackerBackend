package com.Project.PackageTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // Habilita CORS
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita CSRF (opcional para APIs REST)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll() // Permitir acceso público a la página de login
                        .requestMatchers("/api/users/login").permitAll() // Permitir acceso público al endpoint de login
                        .anyRequest().authenticated() // El resto de los endpoints requieren autenticación
                )
                .formLogin(form -> form
                        .loginPage("/") // Especifica la página de login personalizada
                        .loginProcessingUrl("/api/users/login") // Endpoint para procesar el login
                        .defaultSuccessUrl("/main", true) // Redirigir a /main después del login exitoso
                        .permitAll() // Permitir acceso público al formulario de login
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}