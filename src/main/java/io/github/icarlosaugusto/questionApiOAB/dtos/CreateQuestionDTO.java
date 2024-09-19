package io.github.icarlosaugusto.questionApiOAB.dtos;

import io.github.icarlosaugusto.questionApiOAB.entities.Question;
import io.github.icarlosaugusto.questionApiOAB.enums.QuestionType;
import lombok.Data;
import java.util.List;

@Data
public class CreateQuestionDTO {

    private Long subjectId;
    private String questionName;
    private QuestionType questionType;
    private List<AlternativeDTO> alternatives;

    public Question toEntity() {
        Question question = new Question();
        question.setName(questionName);
        question.setQuestionType(questionType);
        return question;
    }
}
