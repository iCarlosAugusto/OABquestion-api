package io.github.icarlosaugusto.questionApiOAB.entities;

import io.jsonwebtoken.Claims;
import lombok.Data;
import java.util.UUID;

@Data
public class JwtUser {

    public JwtUser(Claims claims) {
        this.userId = UUID.fromString(claims.get("userId", String.class));
        this.role = claims.get("role", String.class);
        this.name = claims.get("name", String.class);
        this.email = claims.get("email", String.class);
    }

    private UUID userId;
    private String role;
    private String name;
    private String email;
}
