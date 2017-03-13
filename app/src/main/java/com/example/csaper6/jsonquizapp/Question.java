package com.example.csaper6.jsonquizapp;

import java.util.ArrayList;

/**
 * Created by csaper6 on 3/13/17.
 */
public class Question {
    private String question;
    private String answer;
    private ArrayList<String> answerFalse;
    private String object;
    private String variable;

    public Question (String object, String variable, String a, ArrayList<String> af){
        question = "What is " + object + "'s " + variable + "?";
        answer = a;
        answerFalse = af;
        this.object = object;

        this.variable = variable;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<String> getAnswerFalse() {
        return answerFalse;
    }

    public void setAnswerFalse(ArrayList<String> answerFalse) {
        this.answerFalse = answerFalse;
    }

}
