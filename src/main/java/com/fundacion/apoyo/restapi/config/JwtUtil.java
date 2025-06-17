package com.fundacion.apoyo.restapi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // Inyectar el secret desde application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        // Crear el algoritmo de verificación usando el secret de Supabase
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        
        // Construir el verificador
        JWTVerifier verifier = JWT.require(algorithm)
            .build(); // No necesitamos validar issuer o audience por ahora, solo la firma

        // Verificar el token. Si es inválido, esto lanzará una excepción.
        return verifier.verify(token);
    }
}
