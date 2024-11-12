package io.github.icarlosaugusto.questionApiOAB.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class RepliedQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "question_id")
    @JsonIgnoreProperties({"text", "questionType", "alternatives", "subject", "discipline"})
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"name", "email", "password", "repliedQuestions"})
    private User user;

    private boolean repliedCorrect;
}
