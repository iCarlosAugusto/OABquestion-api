package io.github.icarlosaugusto.questionApiOAB.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.icarlosaugusto.questionApiOAB.enums.QuestionType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private QuestionType questionType;

    @JsonIgnore
    private List<UUID> correctAlternativesId;

    @OneToMany
    private List<Alternative> alternatives;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
}
