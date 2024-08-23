package io.github.icarlosaugusto.questionApiOAB.controller;

import io.github.icarlosaugusto.questionApiOAB.dtos.CreateSubjectDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.Discipline;
import io.github.icarlosaugusto.questionApiOAB.entities.Subject;
import io.github.icarlosaugusto.questionApiOAB.repositories.DisciplineRepository;
import io.github.icarlosaugusto.questionApiOAB.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @PostMapping
    public Discipline createSubject(@RequestBody CreateSubjectDTO createSubjectDTO) {

        Discipline discipline = disciplineRepository.findById(createSubjectDTO.getDisciplineId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Discipline not found")
        );

        List<Subject> disciplineSubjects = discipline.getSubjects();

        Subject subject = createSubjectDTO.toEntity();
        subject.setDisciplines(discipline);
        Subject newSubject = subjectRepository.save(subject);

        disciplineSubjects.add(newSubject);
        return disciplineRepository.save(discipline);
    }

    @GetMapping
    public List<Subject> getSubjects(){
        return subjectRepository.findAll();
    }
}
