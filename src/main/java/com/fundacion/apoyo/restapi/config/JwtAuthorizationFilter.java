package com.fundacion.apoyo.restapi.config;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@org.springframework.lang.NonNull HttpServletRequest request, 
                                    @org.springframework.lang.NonNull HttpServletResponse response, 
                                    @org.springframework.lang.NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        String header = request.getHeader("Authorization");

        // Si el header no existe o no empieza con "Bearer ", continuar sin autenticar
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token (quitando el prefijo "Bearer ")
        String token = header.substring(7);

        try {
            // Validar el token
            DecodedJWT decodedJWT = jwtUtil.validateToken(token);

            // Extraer información del token (claims)
            String email = decodedJWT.getSubject();
            // Supabase incluye el rol en el claim 'role'. Si no, se asigna un rol por defecto.
            String role = decodedJWT.getClaim("role").asString();
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + (role != null ? role.toUpperCase() : "USER")));

            // Crear el objeto de autenticación
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(email, null, authorities);
            
            // Establecer la autenticación en el contexto de seguridad de Spring
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception e) {
            // Si el token es inválido, el contexto de seguridad permanecerá vacío
            // y el acceso será denegado.
            SecurityContextHolder.clearContext();
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}