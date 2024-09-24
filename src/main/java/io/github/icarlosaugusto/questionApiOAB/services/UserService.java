package io.github.icarlosaugusto.questionApiOAB.services;

import io.github.icarlosaugusto.questionApiOAB.dtos.CreateUserDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.User;
import io.github.icarlosaugusto.questionApiOAB.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(CreateUserDTO createUserDTO){
        User user = createUserDTO.toEntity();

        return this.userRepository.save(user);
    }
}
