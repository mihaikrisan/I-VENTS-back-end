package com.mk.ivents.rest.exceptionhandling;

import com.mk.ivents.business.exceptions.*;
import com.mk.ivents.security.exceptions.KeyRetrievalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorMessage> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException methodArgumentNotValidException) {
        List<ValidationError> validationErrors = methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    ValidationError validationError = new ValidationError();
                    validationError.setFieldName(error.getField());
                    validationError.setRejectedValue(error.getRejectedValue());
                    validationError.setErrorMessage(error.getDefaultMessage());

                    return validationError;
                })
                .distinct()
                .collect(Collectors.toList());

        ValidationErrorMessage validationErrorMessage = new ValidationErrorMessage();
        validationErrorMessage.setValidationErrors(validationErrors);

        return ResponseEntity.badRequest().body(validationErrorMessage);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException() {
        return ResponseEntity.badRequest().body("Unprocessable input data");
    }

    @ExceptionHandler(value = SignupException.class)
    public ResponseEntity<String> handleSignUpException(SignupException signUpException) {
        return new ResponseEntity<>(signUpException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException notFoundException) {
        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidVerificationTokenException.class)
    public ResponseEntity<String> handleInvalidVerificationTokenException
            (InvalidVerificationTokenException invalidVerificationTokenException) {
        return ResponseEntity.badRequest().body(invalidVerificationTokenException.getMessage());
    }

    @ExceptionHandler(value = InvalidRefreshTokenException.class)
    public ResponseEntity<String> handleInvalidRefreshTokenException
            (InvalidRefreshTokenException invalidRefreshTokenException) {
        return ResponseEntity.badRequest().body(invalidRefreshTokenException.getMessage());
    }

    @ExceptionHandler(value = OldPasswordException.class)
    public ResponseEntity<String> handleOldPasswordException
            (OldPasswordException oldPasswordException) {
        return ResponseEntity.badRequest().body(oldPasswordException.getMessage());
    }

    @ExceptionHandler(value = KeyRetrievalException.class)
    public ResponseEntity<String> handleKeyRetrievalException(KeyRetrievalException keyRetrievalException) {
        return new ResponseEntity<>(keyRetrievalException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException badCredentialsException,
                                                                      WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTimestamp(Instant.now());
        errorMessage.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorMessage.setError("Unauthorized");
        errorMessage.setMessage(badCredentialsException.getMessage());
        errorMessage.setPath(webRequest.getDescription(false));

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<ErrorMessage> handleDisabledException(DisabledException disabledException,
                                                                WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTimestamp(Instant.now());
        errorMessage.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorMessage.setError("Unauthorized");
        errorMessage.setMessage(disabledException.getMessage());
        errorMessage.setPath(webRequest.getDescription(false));

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException accessDeniedException,
                                                                    WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTimestamp(Instant.now());
        errorMessage.setStatus(HttpStatus.FORBIDDEN.value());
        errorMessage.setError("Forbidden");
        errorMessage.setMessage(accessDeniedException.getMessage());
        errorMessage.setPath(webRequest.getDescription(false));

        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }
}
