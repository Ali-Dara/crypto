package org.dara.authenticationservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailVerificationTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmailVerificationTokenNotFound(EmailVerificationTokenNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(false, exception.getMessage()));
    }

    @ExceptionHandler(EmailVerificationTokenAlreadyUsedException.class)
    public ResponseEntity<ErrorResponse> handleEmailVerificationTokenAlreadyUsed(EmailVerificationTokenAlreadyUsedException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(false, exception.getMessage()));
    }

    @ExceptionHandler(EmailVerificationTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleEmailVerificationTokenExpired(EmailVerificationTokenExpiredException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(false, exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(false, exception.getMessage()));
    }
}
