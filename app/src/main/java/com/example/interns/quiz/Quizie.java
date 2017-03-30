package com.example.interns.quiz;

/**
 * Created by Interns on 3/29/2017.
 */

public class Quizie {

    private String question;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String answerFour;
    private String hint;
    private int correctAnswer;

    public void setCorrectAnswer(int n)
    {
        correctAnswer = n;
    }

    public String getHint() { return hint; }

    public String getQuestion() {
        return question;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public int getCorrectAnswer() { return correctAnswer; }

    public void setHint(String hint) { this.hint = hint; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour = answerFour;
    }
}
