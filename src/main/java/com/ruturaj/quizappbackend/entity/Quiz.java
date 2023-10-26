package com.ruturaj.quizappbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String quizTitle;
    @ManyToMany
    List<Question> questions;

    public Quiz(Integer id, String quizTitle, List<Question> questions) {
        this.id = id;
        this.quizTitle = quizTitle;
        this.questions = questions;
    }
}
