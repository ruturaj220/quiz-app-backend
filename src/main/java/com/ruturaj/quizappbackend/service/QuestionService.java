package com.ruturaj.quizappbackend.service;

import com.ruturaj.quizappbackend.entity.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    ResponseEntity<List<Question>> getAllQuestions();

    ResponseEntity<Question> createQuestion(Question question);

    Question getQuestionById(Long id);

    Question updateQuestion(Long id, Question question) throws Exception;

    void deleteQuestion(Long id);

    ResponseEntity<Question> getQuestionByCategory(String category);

}
