package org.dara.userservice.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("User With UUID:'" + message + "' Not Found.");
    }
}
