package io.github.icarlosaugusto.questionApiOAB.controller;

import io.github.icarlosaugusto.questionApiOAB.dtos.CreateUserDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.User;
import io.github.icarlosaugusto.questionApiOAB.repositories.UserRepository;
import io.github.icarlosaugusto.questionApiOAB.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }
}
