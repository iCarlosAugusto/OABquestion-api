package io.github.icarlosaugusto.questionApiOAB.dtos;

import io.github.icarlosaugusto.questionApiOAB.entities.Discipline;
import lombok.Data;

@Data
public class CreateDisciplineDTO {

    private String name;

    public Discipline toEntity() {
        Discipline discipline = new Discipline();
        discipline.setName(name);
        return discipline;
    }
}
