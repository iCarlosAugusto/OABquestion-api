package io.github.icarlosaugusto.questionApiOAB.controller;

import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.icarlosaugusto.questionApiOAB.dtos.AuthDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.User;
import io.github.icarlosaugusto.questionApiOAB.repositories.UserRepository;
import io.github.icarlosaugusto.questionApiOAB.security.JwtTokenService;
import io.github.icarlosaugusto.questionApiOAB.services.AuthGoogleService;
import io.github.icarlosaugusto.questionApiOAB.services.AuthInternService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthInternService authService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private AuthGoogleService authGoogleService;
    
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthDTO authDTO) {
        User user = authService.authenticate(authDTO);
        if (user != null){
        	String token = jwtTokenService.generateToken(user, "USER"); 
            return ResponseEntity.ok(Map.of("success", true, "token", token, "userId", user.getId(), "email", user.getEmail(), "name", user.getName()));
        } else 
        	return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Credenciais inválidas.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDTO authDTO) {
        Optional<User> userOpt = userRepository.findByEmail(authDTO.getEmail());
        if (userOpt.isPresent()) 
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body("Email já está em uso.");

        User newUser = authService.register(authDTO);
        String token = jwtTokenService.generateToken(newUser, "USER"); 
        return ResponseEntity.ok(Map.of("success", true, "userId", newUser.getId(), "token", token, "email", newUser.getEmail(), "name", newUser.getName()));
    }

    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody Map<String, Object> payload) {
    	String token = (String) payload.get("token");
    	return authGoogleService.authenticate(token);
    }

    // Cancelado por enquanto
    @PostMapping("/apple")
    public ResponseEntity<?> authenticateWithApple(@RequestBody Map<String, Object> payload) {
        String token = (String) payload.get("token");
        
        try {
            Optional<User> userOpt = userRepository.findByTokenApple(token);

            User user;
            if (userOpt.isPresent()) {
                user = userOpt.get();
            } else {
                user = new User();
                user.setTokenApple(token); 
                user = userRepository.save(user); 
            }

            return ResponseEntity.ok().body(Map.of("success", true, "userId", user.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Erro ao verificar o token Apple.");
        }
    }
}
