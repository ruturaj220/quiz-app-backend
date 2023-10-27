package com.ruturaj.quizappbackend.controller;

import com.ruturaj.quizappbackend.entity.QuestionWrapper;
import com.ruturaj.quizappbackend.entity.Quiz;
import com.ruturaj.quizappbackend.entity.Response;
import com.ruturaj.quizappbackend.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "https://ruturaj220.github.io")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int numQ,@RequestParam String quizTitle){

        return this.quizService.createQuiz(category,numQ,quizTitle);
    }

    @GetMapping("/getQuizQuestion/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return this.quizService.getQuizQuestions(id);
    }

    @DeleteMapping("/deleteQuiz/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Integer id){
        return this.quizService.deleteQuiz(id);
    }

    @GetMapping("/getAllQuiz")
    public ResponseEntity<List<Quiz>> getAllQuiz(){
        return this.quizService.getAllQuiz();
    }

    @PutMapping("update/{id}")
    public Quiz updateQuizTitle(@PathVariable Integer id,@RequestBody Quiz quiz){
        return this.quizService.updateQuizTitle(id,quiz);
    }

    @GetMapping("/getQ/{id}")
    public Optional<Quiz> getQuiz(@PathVariable Integer id){
        return this.quizService.getQuiz(id);
    }


    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id,@RequestBody List<Response> responses){
        return this.quizService.calculateResult(id,responses);
    }
}
