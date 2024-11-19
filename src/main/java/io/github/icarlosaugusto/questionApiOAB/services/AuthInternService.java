package io.github.icarlosaugusto.questionApiOAB.services;

import io.github.icarlosaugusto.questionApiOAB.dtos.AuthDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.User;
import io.github.icarlosaugusto.questionApiOAB.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthInternService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 
    

    /**
     * Autentica um usuário com base em e-mail e senha.
     *
     * @param authDTO Objeto contendo e-mail e senha do usuário.
     * @return O usuário autenticado, ou null se as credenciais forem inválidas.
     */
    public User authenticate(AuthDTO authDTO) {
        Optional<User> userOpt = userRepository.findByEmail(authDTO.getEmail());

        if (!userOpt.isPresent()) 
        	return null;
        
        User user = userOpt.get();
        if (passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) 
        	return user;

        return null; 
    }

    /**
     * Registra um novo usuário com base em e-mail e senha.
     *
     * @param authDTO Objeto contendo e-mail e senha do usuário.
     * @return O usuário recém-registrado.
     */
    public User register(AuthDTO authDTO) {
        String encodedPassword = passwordEncoder.encode(authDTO.getPassword());

        User newUser = new User();
        newUser.setEmail(authDTO.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setName(authDTO.getName());

        return userRepository.save(newUser);
    }
}

