package com.omsu.chores_exchanger.handler;

import com.omsu.chores_exchanger.models.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
class ValidationExceptionHandler implements Serializable {
    @ExceptionHandler(value = {UserAlreadyExistException.class})
    @ResponseBody
    protected ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException ex, Locale locale) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {SignInException.class})
    @ResponseBody
    protected ResponseEntity<?> handleSignInException(SignInException ex, Locale locale) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseBody
    protected ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, Locale locale) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ChoreNotFoundException.class})
    @ResponseBody
    protected ResponseEntity<?> handleChoreNotFoundException(ChoreNotFoundException ex, Locale locale) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ServiceException.class})
    @ResponseBody
    protected ResponseEntity<?> handleServiceException(ServiceException ex, Locale locale) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
