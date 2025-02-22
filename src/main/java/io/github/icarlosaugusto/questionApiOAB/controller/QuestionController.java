package io.github.icarlosaugusto.questionApiOAB.controller;

import io.github.icarlosaugusto.questionApiOAB.dtos.CreateQuestionDTO;
import io.github.icarlosaugusto.questionApiOAB.dtos.ValidateQuestionDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.JwtUser;
import io.github.icarlosaugusto.questionApiOAB.entities.Question;
import io.github.icarlosaugusto.questionApiOAB.entities.User;
import io.github.icarlosaugusto.questionApiOAB.repositories.QuestionRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.SubjectRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.UserRepository;
import io.github.icarlosaugusto.questionApiOAB.responses.QuestionResponse;
import io.github.icarlosaugusto.questionApiOAB.responses.ValidateReplyResponse;
import io.github.icarlosaugusto.questionApiOAB.security.JwtTokenService;
import io.github.icarlosaugusto.questionApiOAB.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping
    public Question createQuestion(@RequestBody CreateQuestionDTO createQuestionDTO) {
        return questionService.createQuestion(createQuestionDTO);
    }

    @GetMapping
    public Page<QuestionResponse> getQuestions(
            @RequestParam(required = false) String disciplines,
            @RequestParam(required = false) String subjects,
            @RequestParam(required = false, defaultValue = "all") String myQuestion,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader(value = "Authorization", required = false) String token
    ) {
        JwtUser jwtUser = (token != null) ? jwtTokenService.parseTokenOrNull(token) : null;

        Pageable pageable = PageRequest.of(page, size);

        List<String> disciplinesId = (disciplines != null && !disciplines.isEmpty())
                ? Arrays.asList(disciplines.split("%"))
                : List.of();

        List<String> subjectsId = (subjects != null && !subjects.isEmpty())
                ? Arrays.asList(subjects.split("%"))
                : List.of();

        UUID userId = (jwtUser != null) ? jwtUser.getUserId() : null;

        Page<Question> questions = questionRepository.findQuestionsBySubjectsAndDisciplines(
                disciplinesId.isEmpty() ? null : disciplinesId,
                subjectsId.isEmpty() ? null : subjectsId,
                userId,
                myQuestion,
                pageable
        );

        final User user = (userId != null) ? userRepository.findById(userId).orElse(null) : null;

        return questions.map(el -> el.toQuestionResponse(Optional.ofNullable(user)));
    }

    @PostMapping("/{questionId}/validateReply")
    public ResponseEntity<ValidateReplyResponse> validateReply(
            @PathVariable UUID questionId,
            @RequestBody ValidateQuestionDTO validateQuestionDTO,
            @RequestHeader("Authorization") String token
            ) {
        JwtUser jwtUser = jwtTokenService.parseToken(token);
        return questionService.validateReply(questionId, jwtUser.getUserId(), validateQuestionDTO);
    }
}
