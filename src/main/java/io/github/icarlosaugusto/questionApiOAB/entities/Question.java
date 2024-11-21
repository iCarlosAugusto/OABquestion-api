package io.github.icarlosaugusto.questionApiOAB.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.icarlosaugusto.questionApiOAB.enums.QuestionType;
import io.github.icarlosaugusto.questionApiOAB.responses.QuestionResponse;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String text;

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

    public QuestionResponse toQuestionResponse(Optional<User> user) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(this.id);
        questionResponse.setText(this.text);
        questionResponse.setQuestionType(this.questionType);
        questionResponse.setCorrectAlternativesId(this.correctAlternativesId);
        questionResponse.setAlternatives(this.alternatives);
        questionResponse.setSubjectId(this.subject.getId());
        questionResponse.setDisciplineId(this.discipline.getId());

        if(user.isPresent()){
            Optional<RepliedQuestion> repliedQuestion = user.get().getRepliedQuestions().stream().filter(el -> el.getQuestion().getId().equals(this.id)).findFirst();
            questionResponse.setRepliedCorrect(
                    repliedQuestion.isEmpty() ? null : repliedQuestion.get().isRepliedCorrect()
            );
        }

        return questionResponse;
    }
}

