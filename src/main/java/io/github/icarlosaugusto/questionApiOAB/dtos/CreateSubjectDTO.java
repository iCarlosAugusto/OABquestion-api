package io.github.icarlosaugusto.questionApiOAB.dtos;

import io.github.icarlosaugusto.questionApiOAB.entities.Subject;
import lombok.Data;

@Data
public class CreateSubjectDTO {


    private String subjectName;


    public Subject toEntity() {
        Subject subject = new Subject();
        subject.setName(subjectName);

        return subject;
    }
}
