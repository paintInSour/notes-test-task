package com.service.notes.exception.handler;

import com.service.notes.exception.AuthorizationException;
import com.service.notes.exception.EntityNotFoundException;
import com.service.notes.exception.ServerProcessingException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleException(EntityNotFoundException e) {
        log.error(e.getMessage(), e.getCause());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleException(ConstraintViolationException e) {
        log.error(e.getMessage(), e.getCause());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity handleException(AuthorizationException e) {
        log.error(e.getMessage(), e.getCause());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler({ServerProcessingException.class})
    public ResponseEntity handleException(ServerProcessingException e) {
        log.error(e.getMessage(), e.getCause());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
