package com.github.karixdev.notesapp.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class ValidatorExceptionResponse extends ExceptionResponse {
    private Map<String, String> constraints;
}
