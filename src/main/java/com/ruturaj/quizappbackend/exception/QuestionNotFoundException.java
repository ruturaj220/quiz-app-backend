package com.ruturaj.quizappbackend.exception;

public class QuestionNotFoundException extends RuntimeException{
    public QuestionNotFoundException(Long id) {
        super("Question could not found with this id :" + id);
    }
}
