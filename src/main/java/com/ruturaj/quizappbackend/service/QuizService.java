package com.ruturaj.quizappbackend.service;

import com.ruturaj.quizappbackend.entity.QuestionWrapper;
import com.ruturaj.quizappbackend.entity.Quiz;
import com.ruturaj.quizappbackend.entity.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuizService {
    ResponseEntity<String> createQuiz(String category, int numQ, String quizTitle);

    ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id);

    ResponseEntity<String> deleteQuiz(Integer id);

    ResponseEntity<List<Quiz>> getAllQuiz();

    Quiz updateQuizTitle(Integer id, Quiz quiz);

    Optional<Quiz> getQuiz(Integer id);

    ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses);
}
