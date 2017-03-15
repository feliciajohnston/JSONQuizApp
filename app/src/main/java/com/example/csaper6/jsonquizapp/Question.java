package com.example.csaper6.jsonquizapp;

/**
 * Created by csaper6 on 3/13/17.
 */
public class Question {
    private String question;
    private String answer;
    private String answerFalse1, answerFalse2, answerFalse3;
    private String object;
    private String variable;

    public Question (String object, String variable, String a,String af1, String af2, String af3){
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerFalse1() {
        return answerFalse1;
    }

    public void setAnswerFalse1(String answerFalse1) {
        this.answerFalse1 = answerFalse1;
    }

    public String getAnswerFalse2() {
        return answerFalse2;
    }

    public void setAnswerFalse2(String answerFalse2) {
        this.answerFalse2 = answerFalse2;
    }

    public String getAnswerFalse3() {
        return answerFalse3;
    }

    public void setAnswerFalse3(String answerFalse3) {
        this.answerFalse3 = answerFalse3;
    }
}
