package org.dara.authenticationservice.Exception;

public class EmailVerificationTokenAlreadyUsedException extends RuntimeException {
    public EmailVerificationTokenAlreadyUsedException() {
        super("Email verification token already used");

    }
}
