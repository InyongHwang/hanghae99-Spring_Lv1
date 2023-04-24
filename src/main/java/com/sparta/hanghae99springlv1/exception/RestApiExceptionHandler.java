package com.sparta.hanghae99springlv1.exception;

import com.sparta.hanghae99springlv1.entity.CustomStatus;
import com.sparta.hanghae99springlv1.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public Message handleApiRequestException(IllegalArgumentException ex) {
        return new Message(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({BindException.class})
    public Message bindExceptionHandler(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage());
        }
        return new Message(sb.toString(), HttpStatus.BAD_REQUEST.value());
    }
}
