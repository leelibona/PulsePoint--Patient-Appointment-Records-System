package com.pulsepoint.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * GlobalExceptionHandler — Centralized error handling for all REST controllers.
 *
 * Catches exceptions thrown by the service layer and returns
 * appropriate HTTP responses with descriptive error messages.
 *
 * HTTP Status Codes used:
 * 400 Bad Request     — Invalid input or missing required fields
 * 404 Not Found       — Entity does not exist
 * 409 Conflict        — Business rule violation (e.g. duplicate email, double booking)
 * 500 Internal Error  — Unexpected server error
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors and missing required fields.
     * Returns 400 Bad Request.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of(
                "status", 400,
                "error", "Bad Request",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
    }

    /**
     * Handles business rule violations like double booking or duplicate email.
     * Returns 409 Conflict.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(
            IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of(
                "status", 409,
                "error", "Conflict",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
    }

    /**
     * Handles entity not found errors.
     * Returns 404 Not Found.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of(
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
    }

    /**
     * Handles unsupported operations (e.g. deleting an audit log).
     * Returns 405 Method Not Allowed.
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedOperation(
            UnsupportedOperationException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(Map.of(
                "status", 405,
                "error", "Method Not Allowed",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
    }

    /**
     * Catches any unexpected exceptions.
     * Returns 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of(
                "status", 500,
                "error", "Internal Server Error",
                "message", "An unexpected error occurred. Please try again.",
                "timestamp", LocalDateTime.now().toString()
            ));
    }
}