package org.act.houserentingsystem.exception;

import org.act.houserentingsystem.dto.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .message(errorMessage)
                .data(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }


    // Handle user already exists
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    // Handle other custom exceptions similarly
    // ...

    // Handle DataIntegrityViolationException (for DB unique constraints etc)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = "Data integrity violation";
        if (ex.getMostSpecificCause().getMessage().contains("duplicate key") &&
                ex.getMostSpecificCause().getMessage().contains("email")) {
            message = "Email already exists. Please use a different email.";
        }
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Generic handler for any other exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<?>> handleOtherExceptions(Exception ex) {
//        ApiResponse<?> response = ApiResponse.builder()
//                .success(false)
//                .message("Internal server error: " + ex.getMessage())
//                .data(null)
//                .build();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }
}