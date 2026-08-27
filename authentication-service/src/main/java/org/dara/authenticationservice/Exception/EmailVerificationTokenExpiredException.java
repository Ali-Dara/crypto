package org.dara.authenticationservice.Exception;

public class EmailVerificationTokenExpiredException extends RuntimeException {
    public EmailVerificationTokenExpiredException() {
        super("Email verification token expired");
    }
}
