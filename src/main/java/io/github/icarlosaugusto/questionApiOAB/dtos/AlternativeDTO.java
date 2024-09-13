package io.github.icarlosaugusto.questionApiOAB.dtos;

import io.github.icarlosaugusto.questionApiOAB.entities.Alternative;
import lombok.Data;

import java.util.UUID;

@Data
public class AlternativeDTO {

    private String text;
    private boolean correct;

    public Alternative toEntity() {

        Alternative alternative = new Alternative();
        alternative.setText(text);

        return alternative;
    }
}
