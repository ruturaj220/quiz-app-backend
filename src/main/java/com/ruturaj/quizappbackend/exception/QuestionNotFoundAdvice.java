package com.ruturaj.quizappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class QuestionNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> errorHandler(QuestionNotFoundException exception){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMassage", exception.getMessage());
        return errorMap;
    }
}
