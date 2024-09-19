package io.github.icarlosaugusto.questionApiOAB.services;

import io.github.icarlosaugusto.questionApiOAB.dtos.AlternativeDTO;
import io.github.icarlosaugusto.questionApiOAB.dtos.CreateQuestionDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.Alternative;
import io.github.icarlosaugusto.questionApiOAB.entities.Discipline;
import io.github.icarlosaugusto.questionApiOAB.entities.Question;
import io.github.icarlosaugusto.questionApiOAB.entities.Subject;
import io.github.icarlosaugusto.questionApiOAB.repositories.AlternativeRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.QuestionRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AlternativeRepository alternativeRepository;


    private List<Alternative> createAlternatives(List<AlternativeDTO> alternativesDTO){
        List<Alternative> alternatives = alternativesDTO.stream().map(AlternativeDTO::toEntity).toList();
        return alternativeRepository.saveAll(alternatives);
    }

    private void vinculateAlternativeToQuestion(List<Alternative> alternatives, Question question){
        alternatives.forEach(el -> {
            el.setQuestion(question);
            alternativeRepository.save(el);
        });
    }

    private boolean validateQuestion(CreateQuestionDTO createQuestionDTO) {
        switch (createQuestionDTO.getQuestionType()) {
            case MULTIPLE_CHOICES -> {
                long correctAlternativesCount = createQuestionDTO.getAlternatives().stream()
                        .filter(AlternativeDTO::isCorrect)
                        .count();
                return correctAlternativesCount == 1;
            }
            default -> {
                return false;
            }
        }
    }


    private List<UUID> getCorrectAlternativesId(List<Alternative> alternatives) {
        List<Alternative> correctAlternatives = alternatives.stream().filter(Alternative::isCorrect).toList();
        return correctAlternatives.stream().map(Alternative::getId).toList();
    }

    public Question createQuestion(CreateQuestionDTO createQuestionDTO){
        Subject subject = subjectRepository.findById(createQuestionDTO.getSubjectId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Discipline not found")
        );

        boolean isQuestionValid = this.validateQuestion(createQuestionDTO);
        if(!isQuestionValid){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question body is not correct");
        }

        Discipline discipline = subject.getDisciplines();

        List<Alternative> alternatives = this.createAlternatives(createQuestionDTO.getAlternatives());
        List<UUID> correctAlternativesId = this.getCorrectAlternativesId(alternatives);

        Question question = createQuestionDTO.toEntity();
        question.setDiscipline(discipline);
        question.setSubject(subject);
        question.setAlternatives(alternatives);
        question.setCorrectAlternativesId(correctAlternativesId);

        Question questionCreated = questionRepository.save(question);
        this.vinculateAlternativeToQuestion(alternatives, questionCreated);

        return question;
    }
}
