package io.github.icarlosaugusto.questionApiOAB.dtos;

import io.github.icarlosaugusto.questionApiOAB.entities.Subject;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateSubjectDTO {

    private UUID disciplineId;

    private String subjectName;


    public Subject toEntity() {
        Subject subject = new Subject();
        subject.setName(subjectName);

        return subject;
    }
}
