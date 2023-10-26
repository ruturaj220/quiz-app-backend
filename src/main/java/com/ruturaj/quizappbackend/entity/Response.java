package com.ruturaj.quizappbackend.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {

    private Long Id;
    private String response;
}
