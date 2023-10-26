package com.ruturaj.quizappbackend.exception;

public class QuizNotFoundException extends RuntimeException{
    public QuizNotFoundException(Integer id) {
        super("Quiz could not found with this id :" + id);
    }
}
