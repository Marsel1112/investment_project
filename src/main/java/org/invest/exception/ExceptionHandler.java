package org.invest.exception;

import org.invest.exception.UserResours.NotFoundUserException;
import org.invest.exception.UserResours.UserDuplicatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity<String> handleUserDuplicatedException(UserDuplicatedException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<String> handleNotFoundUserException(NotFoundUserException exception) {

        StringBuilder exceptionMessage = new StringBuilder();
        exceptionMessage.append(exception.getMessage()).append(" ");
        exceptionMessage.append(exception.getLoginUser().getEmail()).append(" ");
        exceptionMessage.append(exception.getLoginUser().getPassword()).append(" ");

        return ResponseEntity.badRequest().body(exceptionMessage.toString());
    }

}
