package io.github.icarlosaugusto.questionApiOAB.services;

import io.github.icarlosaugusto.questionApiOAB.dtos.AuthDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.User;
import io.github.icarlosaugusto.questionApiOAB.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User authenticate(AuthDTO authDTO) {
        return this.userRepository.findByEmailAndPassword(authDTO.getEmail(), authDTO.getPassword()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NO_CONTENT, "User not found")
        );
    }
}
