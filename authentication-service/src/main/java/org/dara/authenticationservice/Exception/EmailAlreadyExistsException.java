package org.dara.authenticationservice.Exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Email '" + email + "' already exists.");
    }
}
