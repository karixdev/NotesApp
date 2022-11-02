package com.github.karixdev.notesapp.exception;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
@Builder
public class ExceptionResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String path;
    private final Map<String, String> errors;
}
