package com.ouharri.market.config;

import com.ouharri.market.exceptions.ResourceException;
import com.ouharri.market.exceptions.ResourceNotCreatedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Global exception handler for handling various exceptions in the API.
 * @author <a href="mailto:ouharrioutman@gmail.com">ouharri outman</a>
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ValidationException {

    /**
     * Handle HttpMessageNotReadableException and return a proper API error response.
     */
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    /**
     * Handle EntityNotFoundException and return a proper API error response.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND, ex);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle MethodArgumentNotValidException and return a map of validation errors.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Handle generic exceptions and return a proper API error response.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception ex) {
        ApiError apiError = new ApiError(NOT_FOUND, ex);
        apiError.setMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle ResourceNotCreatedException and return a map with an error message.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotCreatedException.class)
    public Map<String, String> handleValidationExistsException(ResourceNotCreatedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }
}