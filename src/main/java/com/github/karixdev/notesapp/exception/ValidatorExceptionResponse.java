package com.github.karixdev.notesapp.exception;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ValidatorExceptionResponse extends ExceptionResponse {
    private Map<String, String> constraints;
}
