package io.github.icarlosaugusto.questionApiOAB.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Alternative {

    @Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String text;

    @JsonIgnore
    private boolean correct;

    @ManyToOne()
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;
}
