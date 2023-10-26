package com.ruturaj.quizappbackend.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class QuizNotFoundAdvice {

    public Map<String,String> errorMsgHandler(QuestionNotFoundException exception){
        Map<String,String> errorMsg = new HashMap<>();
        errorMsg.put("Error Message", exception.getMessage());
        return errorMsg;
    }
}
