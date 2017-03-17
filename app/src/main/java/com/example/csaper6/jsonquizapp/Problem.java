package com.example.csaper6.jsonquizapp;

/**
 * Created by csaper6 on 3/17/17.
 */
public class Problem {
    private Question quest;
    private Answer answer, false1, false2, false3;

    public Problem ( Question q, Answer a, Answer f1, Answer f2, Answer f3)
    {
        quest = q;
        answer = a;
        false1 = f1;
        false2 = f2;
        false3 = f3;
    }

    public Question getQuest() {
        return quest;
    }

    public void setQuest(Question quest) {
        this.quest = quest;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Answer getFalse1() {
        return false1;
    }

    public void setFalse1(Answer false1) {
        this.false1 = false1;
    }

    public Answer getFalse2() {
        return false2;
    }

    public void setFalse2(Answer false2) {
        this.false2 = false2;
    }

    public Answer getFalse3() {
        return false3;
    }

    public void setFalse3(Answer false3) {
        this.false3 = false3;
    }

}
