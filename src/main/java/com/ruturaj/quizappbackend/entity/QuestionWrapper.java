package com.ruturaj.quizappbackend.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWrapper {
    private Long queId;
    private String questionTittle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

}
