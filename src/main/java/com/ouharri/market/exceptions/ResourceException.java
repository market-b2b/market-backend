package com.ouharri.market.exceptions;

import com.ouharri.market.entities.User;

/**
 * The parent for all exceptions, associated with resources, such as {@link User} etc.
 *
 * @author Ouharri Outman
 * @version 1.0
 */
public class ResourceException extends RuntimeException {
    public ResourceException() {}

    public ResourceException(String message) {
        super(message);
    }
}
