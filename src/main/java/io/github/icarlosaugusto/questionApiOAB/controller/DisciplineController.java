package io.github.icarlosaugusto.questionApiOAB.controller;

import io.github.icarlosaugusto.questionApiOAB.entities.Discipline;
import io.github.icarlosaugusto.questionApiOAB.repositories.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/discipline")
public class DisciplineController {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @PostMapping
    public Discipline createDiscipline() {
        Discipline discipline = new Discipline();
        discipline.setName("Direito Civil");
        return disciplineRepository.save(discipline);
    }

    @GetMapping
    public List<Discipline> getDisciplines(){
        return disciplineRepository.findAll();
    }
}
