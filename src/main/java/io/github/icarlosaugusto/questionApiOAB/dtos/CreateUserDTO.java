package io.github.icarlosaugusto.questionApiOAB.dtos;

import io.github.icarlosaugusto.questionApiOAB.entities.User;
import lombok.Data;

@Data
public class CreateUserDTO {

    private String name;
    private String email;
    private String password;

    public User toEntity() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }
}
