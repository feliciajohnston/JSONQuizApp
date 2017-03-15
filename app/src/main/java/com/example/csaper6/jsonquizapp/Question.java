package com.example.csaper6.jsonquizapp;

/**
 * Created by csaper6 on 3/13/17.
 */
public class Question {
    private String question;
    private Answer answer, answerFalse1, answerFalse2, answerFalse3;
    private String object;
    private String variable;

    public Question (String object, String variable, Answer a ,Answer af1, Answer af2, Answer af3){
        question = "What is " + object + "'s " + variable + "?";
        answer = a;
        answerFalse1 = af1;
        answerFalse2 = af2;
        answerFalse3 = af3;
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

    public Answer getAnswer() {
        return answer;
    }

    public Answer getAnswerFalse1() {
        return answerFalse1;
    }

    public Answer getAnswerFalse2() {
        return answerFalse2;
    }

    public Answer getAnswerFalse3() {
        return answerFalse3;
    }
}
