package com.fundacion.apoyo.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. APLICAR LA CONFIGURACIÓN DE CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 2. DESHABILITAR CSRF (Común en APIs REST con JWT)
            .csrf(csrf -> csrf.disable())

            // 3. CONFIGURAR LA GESTIÓN DE SESIÓN COMO STATELESS (Sin estado)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 4. CONFIGURAR REGLAS DE AUTORIZACIÓN
            .authorizeHttpRequests(authz -> authz
                // Permite el acceso a todos los endpoints de la API sin autenticación.
                // AJUSTA ESTO SEGÚN TUS NECESIDADES DE SEGURIDAD
                .requestMatchers("/api/**").permitAll() 
                .anyRequest().authenticated()
            );
            
            // Si estás usando filtros JWT, se añadirían aquí. Ejemplo:
            // .addFilterBefore(new JwtAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 5. DEFINE LOS ORÍGENES PERMITIDOS
        // Usamos "http://127.0.0.1:5500" que es el que usará Live Server (ver paso 2).
        // También puedes añadir "*" para permitir todos los orígenes durante el desarrollo.
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http://localhost:5500"));
        
        // 6. DEFINE LOS MÉTODOS HTTP PERMITIDOS (GET, POST, etc.)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // 7. DEFINE LAS CABECERAS PERMITIDAS
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
        
        // 8. PERMITE EL ENVÍO DE CREDENCIALES (como cookies o tokens de autorización)
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica esta configuración a todas las rutas
        
        return source;
    }
}