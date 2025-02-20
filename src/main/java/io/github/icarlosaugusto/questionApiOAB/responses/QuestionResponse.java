package io.github.icarlosaugusto.questionApiOAB.responses;

import io.github.icarlosaugusto.questionApiOAB.entities.Alternative;
import io.github.icarlosaugusto.questionApiOAB.entities.Discipline;
import io.github.icarlosaugusto.questionApiOAB.entities.Subject;
import io.github.icarlosaugusto.questionApiOAB.enums.QuestionType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QuestionResponse {
    private UUID id;

    private String text;

    private QuestionType questionType;

    private List<UUID> correctAlternativesId;

    private List<Alternative> alternatives;

    private Subject subject;

    private Discipline discipline;

    private Boolean repliedCorrect = null;

}