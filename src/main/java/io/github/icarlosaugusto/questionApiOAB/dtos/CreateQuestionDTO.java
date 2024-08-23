package io.github.icarlosaugusto.questionApiOAB.dtos;

import io.github.icarlosaugusto.questionApiOAB.entities.Question;
import io.github.icarlosaugusto.questionApiOAB.entities.Subject;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateQuestionDTO {

    private UUID subjectId;

    private String questionName;


    public Question toEntity() {
        Question question = new Question();
        question.setName(questionName);

        return question;
    }
}
