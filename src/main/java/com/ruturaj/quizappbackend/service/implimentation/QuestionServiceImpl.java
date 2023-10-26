    package com.ruturaj.quizappbackend.service.implimentation;

    import com.ruturaj.quizappbackend.entity.Question;
    import com.ruturaj.quizappbackend.exception.QuestionNotFoundException;
    import com.ruturaj.quizappbackend.repository.QuestionRepository;
    import com.ruturaj.quizappbackend.service.QuestionService;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.dao.DataIntegrityViolationException;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;

    @Service
    public class QuestionServiceImpl implements QuestionService {

        private final QuestionRepository questionRepository;
        private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

        @Autowired
        public QuestionServiceImpl(QuestionRepository questionRepository) {
            this.questionRepository = questionRepository;
        }

        @Override
        public ResponseEntity<List<Question>> getAllQuestions() {

            try {
                return new ResponseEntity<>(this.questionRepository.findAll(),HttpStatus.OK);
            }catch (Exception e){
                logger.error("An error occurred while fetching all questions", e);
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @Override
        public ResponseEntity<Question> createQuestion(Question question) {

            try {
                // Check if a question with the same content already exists
                Question existingQuestion = questionRepository.findByQuestionTittle(question.getQuestionTittle());

                if (existingQuestion != null) {
                    // If a question with the same content exists, return a conflict response
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }

                // If no question with the same content exists, save the new question
                Question createdQuestion = this.questionRepository.save(question);
                return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                // Handle database integrity violation exception (e.g., unique constraint violation)
                logger.error("An error occurred while creating a question", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                logger.error("An error occurred while creating a question", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        @Override
        public Question getQuestionById(Long id) {
            return questionRepository.findById(id)
                    .orElseThrow(() -> new QuestionNotFoundException(id));
        }

        @Override
        public Question updateQuestion(Long id, Question updatedQuestion) throws Exception{
            return (Question) this.questionRepository.findById(id).map((question)->{
                question.setQuestionTittle(updatedQuestion.getQuestionTittle());
                question.setOption1(updatedQuestion.getOption1());
                question.setOption2(updatedQuestion.getOption2());
                question.setOption3(updatedQuestion.getOption3());
                question.setOption4(updatedQuestion.getOption4());
                question.setCorrectOption(updatedQuestion.getCorrectOption());
                question.setCategory(updatedQuestion.getCategory());
                return (Question)questionRepository.save(question);
            }).orElseThrow(()-> new QuestionNotFoundException(id));


        }

        @Override
        public void deleteQuestion(Long id) {

            if(!this.questionRepository.existsById(id)){
                throw new QuestionNotFoundException(id);
            }else {
                this.questionRepository.deleteById(id);
            }
        }

        @Override
        public ResponseEntity<Question> getQuestionByCategory(String category) {

            try {
                return new ResponseEntity<>(this.questionRepository.getQuestionByCategory(category),HttpStatus.OK);
            }catch (Exception e){
                logger.error("An error occurred while fetching all questions", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


    }
