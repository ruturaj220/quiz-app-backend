package com.ruturaj.quizappbackend.service.implimentation;

import com.ruturaj.quizappbackend.entity.*;
import com.ruturaj.quizappbackend.exception.QuizNotFoundException;
import com.ruturaj.quizappbackend.repository.QuestionRepository;
import com.ruturaj.quizappbackend.repository.QuizRepository;
import com.ruturaj.quizappbackend.repository.UserRepository;
import com.ruturaj.quizappbackend.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;


    public QuizServiceImpl(QuizRepository quizRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> createQuiz(String category, int numQ, String quizTitle) {

        try {
            List<Question> questions = questionRepository.findRandomQuestionByCategory(category, numQ);
            Quiz quiz = new Quiz();
            quiz.setQuizTitle(quizTitle);
            quiz.setQuestions(questions);
            quizRepository.save(quiz);



            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle the exception here, you can log it or perform other actions
            logger.error("An error occurred while creating the quiz", e);
            return new ResponseEntity<>("Failed to create the quiz", HttpStatus.INTERNAL_SERVER_ERROR); // Return an appropriate response for the exception
        }
    }

    @Override
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            List<Question> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionForUser = new ArrayList<>();
            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(q.getQueId(), q.getQuestionTittle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionForUser.add(qw);
            }

            return new ResponseEntity<>(questionForUser, HttpStatus.OK);
        } else {
            // Handle the case when the Optional is empty.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<String> deleteQuiz(Integer id) {
        this.quizRepository.deleteById(id);
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Quiz>> getAllQuiz() {
        return new ResponseEntity<>(this.quizRepository.findAll(),HttpStatus.OK);
    }

    @Override
    public Quiz updateQuizTitle(Integer id, Quiz updatedQuiz) {
        return (Quiz)this.quizRepository.findById(id).map((quiz) -> {
            quiz.setQuizTitle(updatedQuiz.getQuizTitle());
            return (Quiz)this.quizRepository.save(quiz);
        }).orElseThrow(()-> new QuizNotFoundException(id));
    }

    @Override
    public Optional<Quiz> getQuiz(Integer id) {
        return this.quizRepository.findById(id);
    }

    /*@Override
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        try {
            Optional<Quiz> optionalQuiz = this.quizRepository.findById(id);
            if (optionalQuiz.isPresent()) {
                Quiz quiz = optionalQuiz.get();
                List<Question> questions = quiz.getQuestions();

                int right = 0;
                int i = 0;

                for (Response response : responses) {
                    System.out.println(response.getId());

                    if (i < questions.size() && response.getResponse().equals(questions.get(i).getCorrectOption())) {
                        right++;
                    }
                    i++;
                }
                return new ResponseEntity<>(right, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @Override
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        try {
            Optional<Quiz> optionalQuiz = this.quizRepository.findById(id);
            if (optionalQuiz.isPresent()) {
                Quiz quiz = optionalQuiz.get();
                List<Question> questions = quiz.getQuestions();

                int score = 0;

                for (int i = 0; i < responses.size(); i++) {
                    Response response = responses.get(i);
                    System.out.println(response.getId());

                    if (i < questions.size()) {
                        String correctOption = questions.get(i).getCorrectOption();
                        if (response.getResponse() == null || response.getResponse().isEmpty()) {
                            // If the response is empty or null, consider it an unanswered question
                            score = 0;
                            break; // Break the loop if any question is left unanswered
                        } else if (response.getResponse().equals(correctOption)) {
                            // If the response is correct, increment the score
                            score++;
                        }
                        // Do not deduct points for wrong answers
                    }
                }


                return new ResponseEntity<>(score, HttpStatus.OK);
            } else {
                // Handle the case where the quiz is not found
                return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
