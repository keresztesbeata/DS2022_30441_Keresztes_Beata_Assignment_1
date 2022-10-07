package lab.ds2022_assignment_1.controllers.handlers;

import lab.ds2022_assignment_1.model.exceptions.DuplicateUsernameException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.io.IOException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {
        return handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }

    @ExceptionHandler(value = {DuplicateUsernameException.class})
    public ResponseEntity<Object> handleDuplicateUsernameException(DuplicateUsernameException e, WebRequest request) {
        return handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException e, WebRequest request) {
        return handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e, WebRequest request) {
        return handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED,
                request
        );
    }

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<Object> handleIOException(IOException e, WebRequest request) {
        return handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }
}
