package com.ouharri.market.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class representing the structure of API error responses.
 * @author <a href="mailto:ouharrioutman@gmail.com">ouharri outman</a>
 */
@Data
class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    /**
     * Constructor for creating an API error with a specific HTTP status.
     */
    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    /**
     * Constructor for creating an API error with a specific HTTP status and exception.
     */
    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    /**
     * Constructor for creating an API error with a specific HTTP status, message, and exception.
     */
    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}