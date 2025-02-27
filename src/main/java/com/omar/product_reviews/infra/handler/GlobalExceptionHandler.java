package com.omar.product_reviews.infra.handler;

import com.omar.product_reviews.exceptions.EntityAlreadyExistsException;
import com.omar.product_reviews.exceptions.UserAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<ErrorMessage> handler(UsernameNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorMessage(
                        LocalDateTime.now(),
                        "Username not found",
                        exception.getMessage(),
                        HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value()
                )
        );
    }

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<ErrorMessage> handler(ValidationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorMessage(
                        LocalDateTime.now(),
                        "Validation exception",
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.value()
                )
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<ErrorMessage> handler(UserAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorMessage(
                        LocalDateTime.now(),
                        "User already exists exception",
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.value()
                )
        );
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    private ResponseEntity<ErrorMessage> handler(EntityAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorMessage(
                        LocalDateTime.now(),
                        "Entity already exists exception",
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.value()
                )
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorMessage> handler(EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorMessage(
                        LocalDateTime.now(),
                        "Entity not found exception",
                        exception.getMessage(),
                        HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value()
                )
        );
    }

}
