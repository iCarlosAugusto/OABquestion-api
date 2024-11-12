package io.github.icarlosaugusto.questionApiOAB.dtos;

import io.github.icarlosaugusto.questionApiOAB.entities.Question;
import io.github.icarlosaugusto.questionApiOAB.enums.QuestionType;
import lombok.Data;
import java.util.List;

@Data
public class CreateQuestionDTO {

    private Long subjectId;
    private String text;
    private QuestionType questionType = QuestionType.MULTIPLE_CHOICES;
    private List<AlternativeDTO> alternatives;

    public Question toEntity() {
        Question question = new Question();
        question.setText(text);
        question.setQuestionType(questionType);
        return question;
    }
}
