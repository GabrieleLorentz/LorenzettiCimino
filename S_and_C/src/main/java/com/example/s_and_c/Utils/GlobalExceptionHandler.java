package com.example.s_and_c.Utils;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private record ErrorDto(String message, int status) {}

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDto> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Operazione non valida: ";
        if (ex.getCause() instanceof ConstraintViolationException) {
            message += "violazione di un vincolo unique";
        }

        ErrorDto errorResponse = new ErrorDto(message, HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handleNotFound(EntityNotFoundException ex) {
        ErrorDto errorResponse = new ErrorDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}