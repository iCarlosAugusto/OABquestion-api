package io.github.icarlosaugusto.questionApiOAB.responses;

import lombok.Data;

import java.util.UUID;

@Data
public class ValidateReplyResponse {

    private UUID correctAlternativeId;
    private boolean isReplyCorrect;
}
