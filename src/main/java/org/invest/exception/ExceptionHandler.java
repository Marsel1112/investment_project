package org.invest.exception;

import org.invest.exception.UserResours.NotFoundUserException;
import org.invest.exception.UserResours.UserDuplicatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
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

    @org.springframework.web.bind.annotation.ExceptionHandler(TooManyDaysException.class)
    public ResponseEntity<String> tooManyDaysException(TooManyDaysException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TickerNotFoundException.class)
    public ResponseEntity<String> tickerNotFoundException(TickerNotFoundException exception) {
        return ResponseEntity.badRequest().body("Тикер акции " + exception.getMessage() + " не найден!");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> tickerNotFoundException(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body("Неправильные данные, проверьте дату и числа!");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PolygonProhibitedException.class)
    public ResponseEntity<String> tickerNotFoundException(PolygonProhibitedException exception) {
        return ResponseEntity.notFound().build();
    }

}
