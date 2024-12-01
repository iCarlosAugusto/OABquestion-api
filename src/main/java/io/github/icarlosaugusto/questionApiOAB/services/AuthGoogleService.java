package io.github.icarlosaugusto.questionApiOAB.services;

import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.icarlosaugusto.questionApiOAB.entities.User;
import io.github.icarlosaugusto.questionApiOAB.repositories.UserRepository;
import io.github.icarlosaugusto.questionApiOAB.security.JwtTokenService;

@Service
public class AuthGoogleService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtTokenService jwtTokenService;
    
    @Value("${google.client.id}") 
    private String googleClientId;
    
    /**
     * Autentica um usuário (Login ou SignUp) com base no TOKEN da conta google
     *
     * @param token Objeto contendo o token da conta google
     * @return O usuário autenticado, ou UNAUTHORIZED se as credenciais forem inválidas.
     */
    public ResponseEntity<?> authenticate(String accessToken) {
        try {
            // Utilize o accessToken para obter as informações do usuário do Google
            String userInfoEndpoint = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;
            
            // Fazer uma requisição HTTP GET para obter informações do usuário
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.getForEntity(userInfoEndpoint, Map.class);

            if (!response.getStatusCode().isError()) {
                Map<String, Object> userInfo = response.getBody();
                
                // Fazer login ou registrar o usuário com base nas informações obtidas
                User user = loginOrRegister(userInfo);
                String tokenSessao = jwtTokenService.generateToken(user, "USER"); // Exemplo: "USER" como role
                return ResponseEntity.ok().body(Map.of("success", true, "userId", user.getId(), "token", tokenSessao, "email", user.getEmail(), "name", user.getName()));
            } else {
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Access token inválido.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Erro ao verificar o token de acesso.");
        }
    }



	private User loginOrRegister( Map<String, Object>  userInfo) {
        
        // Extraia informações do usuário
        String userId = (String) userInfo.get("id");
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        

		Optional<User> userOpt = userRepository.findByTokenGoogle(userId);

		if (userOpt.isPresent()) 
		    return login(userOpt);
		else 
		    return register(userId, email, name); 
	}

	private User register(String userId, String email, String name) {
		User user;
		user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setTokenGoogle(userId); 
		user = userRepository.save(user);
		return user;
	}
	
	private User login(Optional<User> userOpt) {
		return userOpt.get();
	}
}
