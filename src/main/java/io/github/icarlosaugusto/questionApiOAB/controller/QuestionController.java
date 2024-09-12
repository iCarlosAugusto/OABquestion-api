package io.github.icarlosaugusto.questionApiOAB.controller;

import io.github.icarlosaugusto.questionApiOAB.dtos.CreateQuestionDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.Discipline;
import io.github.icarlosaugusto.questionApiOAB.entities.Question;
import io.github.icarlosaugusto.questionApiOAB.entities.Subject;
import io.github.icarlosaugusto.questionApiOAB.repositories.DisciplineRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.QuestionRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    public Page<Question> getQuestions(
            @RequestParam(required = false) String disciplines,
            @RequestParam(required = false) String subjects,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);

        List<String> disciplinesId = Arrays.asList(disciplines.split("%"));
        List<String> subjectsId = Arrays.asList(subjects.split("%"));
        System.out.println(disciplinesId);
        System.out.println(subjectsId);
        return questionRepository.findQuestionsBySubjectsAndDisciplines(
                disciplinesId,
                subjectsId,
                pageable
        );
    }
}
