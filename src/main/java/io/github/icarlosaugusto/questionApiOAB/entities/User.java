package io.github.icarlosaugusto.questionApiOAB.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;

    @JsonIgnore
    private String password;
    
    private String tokenGoogle;
    private String tokenApple;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"id", "user"})
    private List<RepliedQuestion> repliedQuestions;
}