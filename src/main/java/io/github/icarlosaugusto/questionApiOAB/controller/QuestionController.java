package io.github.icarlosaugusto.questionApiOAB.controller;

import io.github.icarlosaugusto.questionApiOAB.dtos.CreateQuestionDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.Question;
import io.github.icarlosaugusto.questionApiOAB.repositories.AlternativeRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.DisciplineRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.QuestionRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.SubjectRepository;
import io.github.icarlosaugusto.questionApiOAB.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @Autowired
    private AlternativeRepository alternativeRepository;

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public Question createQuestion(@RequestBody CreateQuestionDTO createQuestionDTO) {
        return questionService.createQuestion(createQuestionDTO);
    }

    @GetMapping
    public Page<Question> getQuestions(
            @RequestParam(required = false) String disciplines,
            @RequestParam(required = false) String subjects,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        List<String> disciplinesId = (disciplines != null && !disciplines.isEmpty())
                ? Arrays.asList(disciplines.split("%"))
                : List.of();

        List<String> subjectsId = (subjects != null && !subjects.isEmpty())
                ? Arrays.asList(subjects.split("%"))
                : List.of();

        return questionRepository.findQuestionsBySubjectsAndDisciplines(
                disciplinesId.isEmpty() ? null : disciplinesId,
                subjectsId.isEmpty() ? null : subjectsId,
                pageable
        );
    }

}
