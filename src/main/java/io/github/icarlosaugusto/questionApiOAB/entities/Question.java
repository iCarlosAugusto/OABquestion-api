package io.github.icarlosaugusto.questionApiOAB.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.mapping.Join;

import java.util.UUID;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
}
