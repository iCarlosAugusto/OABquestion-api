package io.github.icarlosaugusto.questionApiOAB.controller;

import io.github.icarlosaugusto.questionApiOAB.dtos.CreateDisciplineDTO;
import io.github.icarlosaugusto.questionApiOAB.entities.Discipline;
import io.github.icarlosaugusto.questionApiOAB.repositories.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discipline")
public class DisciplineController {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @PostMapping
    public Discipline createDiscipline(
            @RequestBody CreateDisciplineDTO createDisciplineDTO
    ) {
        Discipline discipline = createDisciplineDTO.toEntity();
        return disciplineRepository.save(discipline);
    }

    @GetMapping
    public List<Discipline> getDisciplines(){
        return disciplineRepository.findAll();
    }
}
