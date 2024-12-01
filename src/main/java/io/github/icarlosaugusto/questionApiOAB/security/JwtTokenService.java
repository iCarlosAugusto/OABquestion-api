package io.github.icarlosaugusto.questionApiOAB.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.icarlosaugusto.questionApiOAB.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {

    private static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos
    
    private final Key secretKey;
    
    public JwtTokenService(@Value("${jwt.secret}") String secret) {
    	this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateToken(User user, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); 
        claims.put("idUser", user.getId());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        
        return Jwts.builder()
                .setClaims(claims) 
                .setSubject(user.getName()) 
                .setIssuedAt(new Date(System.currentTimeMillis())) 
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey) 
                .compact(); 
    }
}
