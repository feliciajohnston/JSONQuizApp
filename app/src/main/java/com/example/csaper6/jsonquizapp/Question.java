package com.example.csaper6.jsonquizapp;

/**
 * Created by csaper6 on 3/13/17.
 */
public class Question {
    private String question;
    private String object;
    private String variable;

    public Question (String object, String variable){
        question = "What is " + object + "'s " + variable + "?";
        this.object = object;
        this.variable = variable;
    }

    public String getObject() {
        return object;
    }

    public String getVariable() {
        return variable ;
    }

    public String getQuestion() {
        return question;
    }

}
