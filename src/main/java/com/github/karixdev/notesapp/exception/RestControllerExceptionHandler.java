package com.github.karixdev.notesapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(exception.getMessage())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValid(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {

        Map<String, String> constraintsMap = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(fieldError -> fieldError.getDefaultMessage() != null)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Provided data is not valid")
                        .path(request.getRequestURI())
                        .errors(constraintsMap)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> illegalArgument(
            IllegalArgumentException exception,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Provided illegal arguments")
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}
