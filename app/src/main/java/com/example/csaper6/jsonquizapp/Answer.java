package com.example.csaper6.jsonquizapp;

/**
 * Created by csaper6 on 3/15/17.
 */
public class Answer {
    private String answer;
    private Boolean isCorrect;

    public Answer(String s, Boolean b){
        answer = s;
        isCorrect = b;

    }

    public String getAnswer() {
        return answer;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }
}
