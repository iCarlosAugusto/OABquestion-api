package io.github.icarlosaugusto.questionApiOAB.controller;

import io.github.icarlosaugusto.questionApiOAB.dtos.CreateQuestionDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.Discipline;
import io.github.icarlosaugusto.questionApiOAB.entities.Question;
import io.github.icarlosaugusto.questionApiOAB.entities.Subject;
import io.github.icarlosaugusto.questionApiOAB.repositories.DisciplineRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.QuestionRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public Question createQuestion(@RequestBody CreateQuestionDTO createQuestionDTO) {
        Subject subject = subjectRepository.findById(createQuestionDTO.getSubjectId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Discipline not found")
        );

        Discipline discipline = subject.getDisciplines();

        Question question = createQuestionDTO.toEntity();
        question.setDiscipline(discipline);
        question.setSubject(subject);

        Question questionCreated = questionRepository.save(question);

        List<Question> subjectQuestions = subject.getQuestions();
        subjectQuestions.add(questionCreated);
        return question;
    }

    @GetMapping
    public List<Question> getQuestions(){
        return questionRepository.findAll();
    }
}
