package com.ruturaj.quizappbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queId;
    @Column(nullable = false)
    private String questionTittle;
    @Column(nullable = false)
    private String option1;
    @Column(nullable = false)
    private String option2;
    @Column(nullable = false)
    private String option3;
    @Column(nullable = false)
    private String option4;
    @Column(nullable = false)
    private String correctOption;
    @Column(nullable = false)
    private String category;
}
