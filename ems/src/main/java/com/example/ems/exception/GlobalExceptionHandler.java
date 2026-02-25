package com.example.ems.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCityException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCity(
            InvalidCityException ex,
            HttpServletRequest request
    ) {
        Map<String, String> details = new HashMap<>();
        details.put("city", ex.getMessage());
        log.warn("Business validation failed for request {} {}: {}", request.getMethod(), request.getRequestURI(),
                ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid city", ex.getMessage(), details, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        log.warn("Request validation failed for {} {}: {}", request.getMethod(), request.getRequestURI(), errors);
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                "One or more fields are invalid",
                errors,
                request
        );
    }

    @ExceptionHandler(InvalidDepartmentException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDepartment(
            InvalidDepartmentException ex,
            HttpServletRequest request
    ) {
        log.warn("Invalid department on {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid department", ex.getMessage(), Map.of(), request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("Resource not found on {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage(), Map.of(), request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String message = "Invalid request parameter: " + ex.getName();
        log.warn("Type mismatch on {} {}: {}", request.getMethod(), request.getRequestURI(), message);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request parameter", message, Map.of(), request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParam(
            MissingServletRequestParameterException ex,
            HttpServletRequest request
    ) {
        String message = "Missing required parameter: " + ex.getParameterName();
        log.warn("Missing request parameter on {} {}: {}", request.getMethod(), request.getRequestURI(), message);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Missing parameter", message, Map.of(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        log.error("Database integrity violation on {} {}", request.getMethod(), request.getRequestURI(), ex);
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Data conflict",
                "Requested operation violates database constraints",
                Map.of(),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("FATAL application exception on {} {}", request.getMethod(), request.getRequestURI(), ex);
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                "Unexpected server error occurred",
                Map.of(),
                request
        );
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(
            HttpStatus status,
            String errorTitle,
            String message,
            Map<String, String> details,
            HttpServletRequest request
    ) {
        String requestId = MDC.get("requestId");
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                status.value(),
                errorTitle,
                message,
                request.getRequestURI(),
                requestId == null ? "" : requestId,
                details
        );
        return new ResponseEntity<>(body, status);
    }
}
