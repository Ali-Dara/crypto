package org.dara.authenticationservice.Exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username '" + username + "' already exists.");
    }
}
