package org.spring.cp.springprojectmay2024.error;

import org.spring.cp.springprojectmay2024.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<ErrorDTO> handleAuthenticationServiceException(Exception ex){
        return ResponseEntity
                .internalServerError()
                .body(ErrorDTO
                        .builder()
                        .message(ex.getMessage())
                        .time(OffsetDateTime.now())
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDTO> handleBadCredentialsException(BadCredentialsException ex){
        return ResponseEntity
                .internalServerError()
                .body(ErrorDTO
                        .builder()
                        .message("Invalid username or password")
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

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        return ResponseEntity
                .status(400)
                .body(ErrorDTO
                        .builder()
                        .message("Username already exists")
                        .time(OffsetDateTime.now())
                        .build());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity
                .status(404)
                .body(ErrorDTO
                        .builder()
                        .message("Users not found")
                        .time(OffsetDateTime.now())
                        .build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(ErrorDTO
                        .builder()
                        .message("User not found")
                        .time(OffsetDateTime.now())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(404)
                .body(ErrorDTO
                        .builder()
                        .message(ex.getMessage())
                        .time(OffsetDateTime.now())
                        .build());
    }
}
