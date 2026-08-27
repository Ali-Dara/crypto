package org.dara.authenticationservice.Exception;

public class EmailVerificationTokenNotFoundException extends RuntimeException {
    public EmailVerificationTokenNotFoundException() {
        super("Email verification token not found");
    }
}
