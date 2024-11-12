package io.github.icarlosaugusto.questionApiOAB.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class ValidateQuestionDTO {

    private UUID alternativeId;
    private UUID userId;
}
