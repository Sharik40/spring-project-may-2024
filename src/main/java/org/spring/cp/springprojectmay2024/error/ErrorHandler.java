package org.spring.cp.springprojectmay2024.controller;

import org.spring.cp.springprojectmay2024.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception ex){
        return ResponseEntity
                .internalServerError()
                .body(ErrorDTO
                        .builder()
                        .message(ex.getMessage())
                        .time(OffsetDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String details = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField().concat(" ").concat(Objects.requireNonNull(error.getDefaultMessage())))
                .collect(Collectors.joining(", \n"));

        return ResponseEntity
                .badRequest()
                .body(ErrorDTO.builder()
                        .message(details)
                        .time(OffsetDateTime.now())
                        .build()
                );
    }
}
