package io.github.icarlosaugusto.questionApiOAB.services;

import io.github.icarlosaugusto.questionApiOAB.dtos.AlternativeDTO;
import io.github.icarlosaugusto.questionApiOAB.dtos.CreateQuestionDTO;
import io.github.icarlosaugusto.questionApiOAB.dtos.ValidateQuestionDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.*;
import io.github.icarlosaugusto.questionApiOAB.repositories.AlternativeRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.QuestionRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.SubjectRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.UserRepository;
import io.github.icarlosaugusto.questionApiOAB.responses.ValidateReplyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserRepository userRepository;


    private List<Alternative> createAlternatives(List<AlternativeDTO> alternativesDTO){
        List<Alternative> alternatives = alternativesDTO.stream().map(AlternativeDTO::toEntity).toList();
        for (int i = 0; i < alternatives.size(); i++) {
            alternatives.get(i).setAlternativeLetterByIndex(i);
        }
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


    private void saveQuestionInUserHistory(User user, Question question, boolean repliedCorrect) {
        List<RepliedQuestion> userRepliedQuestions = user.getRepliedQuestions();

        RepliedQuestion newRepliedQuestion = new RepliedQuestion();
        newRepliedQuestion.setQuestion(question);
        newRepliedQuestion.setUser(user);
        newRepliedQuestion.setRepliedCorrect(repliedCorrect);

        userRepliedQuestions.add(newRepliedQuestion);
        userRepository.save(user);

//        boolean userAlreadyReplied = userRepliedQuestions.stream().anyMatch(el -> el.getQuestion().getId().equals(question.getId()));
//        if(!userAlreadyReplied){
//            userRepliedQuestions.add(newRepliedQuestion);
//        }else{
//            Optional<RepliedQuestion> repliedQuestion = userRepliedQuestions.stream()
//                    .filter(el -> el.getQuestion.getQuestionId().equals(questionId))
//                    .findFirst();
//            repliedQuestion.get().setRepliedCorrect(pickedAlternative.isCorrect());
//        }

    }

    public ResponseEntity<ValidateReplyResponse> validateReply(UUID questionId, ValidateQuestionDTO validateQuestionDTO) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Questão não existe")
        );

        Alternative pickedAlternative = question.getAlternatives().stream().filter(
                el -> el.getId().equals(validateQuestionDTO.getAlternativeId())
        ).findFirst().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questão não contém nenhuma alternativa com o id oferecido")
        );


        Alternative correctAlternative = question.getAlternatives().stream().filter(
                Alternative::isCorrect
        ).findFirst().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questão não contém nenhuma alternativa com o id oferecido")
        );



        User user = userRepository.findById(validateQuestionDTO.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não enconcotrado para o ID fornecido")
        );

        this.saveQuestionInUserHistory(user, question, pickedAlternative.isCorrect());
//
//        List<RepliedQuestion> userRepliedQuestions = user.getRepliedQuestion();
//
//        RepliedQuestion newRepliedQuestion = new RepliedQuestion();
//        newRepliedQuestion.setQuestionId(questionId);
//        newRepliedQuestion.setRepliedCorrect(pickedAlternative.isCorrect());
//
//        boolean userAlreadyReplied = userRepliedQuestions.stream().anyMatch(el -> el.getQuestionId().equals(questionId));
//        if(!userAlreadyReplied){
//            userRepliedQuestions.add(newRepliedQuestion);
//        }else{
//            Optional<RepliedQuestion> repliedQuestion = userRepliedQuestions.stream()
//                    .filter(el -> el.getQuestionId().equals(questionId))
//                    .findFirst();
//            repliedQuestion.get().setRepliedCorrect(pickedAlternative.isCorrect());
//        }
//
//        userRepository.save(user);

        ValidateReplyResponse validateReplyResponse = new ValidateReplyResponse();
        validateReplyResponse.setReplyCorrect(pickedAlternative.isCorrect());
        validateReplyResponse.setCorrectAlternativeId(correctAlternative.getId());

        return ResponseEntity.ok(validateReplyResponse);
    }
}
