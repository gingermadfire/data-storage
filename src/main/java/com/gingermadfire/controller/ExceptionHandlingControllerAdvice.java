package com.gingermadfire.controller;

import com.gingermadfire.exception.NotFoundException;
import com.gingermadfire.exception.PasswordMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<Object> array = new ArrayList<>();

        for (Object argument : e.getDetailMessageArguments()) {
            if (argument != null) {
                if (argument instanceof Object[] a) {
                    if (a.length == 0) {
                        continue;
                    }
                }
                array.add(argument);
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(array);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> handlePasswordMismatchException() {
        //TODO:логирование
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Введенный пароль не совпадает");
    }

}
