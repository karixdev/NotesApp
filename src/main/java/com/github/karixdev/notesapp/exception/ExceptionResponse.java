package com.github.karixdev.notesapp.exception;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
@Builder
public class ExceptionResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String path;

    @Builder.Default
    private final Map<String, String> errors = new HashMap<>();
}
