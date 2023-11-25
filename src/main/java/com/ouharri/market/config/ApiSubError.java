package com.ouharri.market.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Abstract class representing sub-errors in API error responses.
 * @author <a href="mailto:ouharrioutman@gmail.com">ouharri outman</a>
 */
abstract class ApiSubError {

}

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    /**
     * Constructor for creating a validation error.
     * @author <a href="mailto:ouharrioutman@gmail.com">ouharri outman</a>
     */
    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}