package com.yunyan.project.authorization.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yunyan.project.authorization.dto.commons.FieldErrorDTO;
import com.yunyan.project.authorization.dto.commons.ResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors (e.g., @Valid annotations)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<List<FieldErrorDTO>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldErrorDTO> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorDTO(error.getField(), List.of(error.getDefaultMessage())))
                .collect(Collectors.toList());

        return buildValidationErrorResponse(fieldErrors, "Input field validation failed", HttpStatus.BAD_REQUEST);
    }

    // Handle database constraint violations (e.g., unique constraint)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO<List<FieldErrorDTO>>> handleDatabaseExceptions(DataIntegrityViolationException ex) {
        FieldErrorDTO fieldError = new FieldErrorDTO("Database", List.of("A database error occurred, possibly due to duplicate data."));
        return buildValidationErrorResponse(List.of(fieldError), "Database constraint violation", HttpStatus.CONFLICT);
    }

    // Handle all other generic exceptions
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<List<FieldErrorDTO>>> handleGeneralExceptions(Exception ex) {
        FieldErrorDTO fieldError = new FieldErrorDTO("General", List.of("An unexpected error occurred."));
        return buildValidationErrorResponse(List.of(fieldError), "An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to build the error response
    @SuppressWarnings("rawtypes")
    private ResponseEntity<ResponseDTO<List<FieldErrorDTO>>> buildValidationErrorResponse(List<FieldErrorDTO> fieldErrors, String message, HttpStatus status) {
        ResponseDTO<List<FieldErrorDTO>> response = new ResponseDTO<>();
        response.setStatusCode(status.value());
        response.setMessage(message);
        response.setStatus(false);
        response.setData(fieldErrors);

        return new ResponseEntity<>(response, status);
    }
}

