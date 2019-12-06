package com.example.lab2;

import java.io.Serializable;

public class Question implements Serializable {
    private String text;
    private boolean answer;
    private boolean cheated;
    private boolean answered = false;

    public Question(String text, boolean answer) {
        this.text = text;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public boolean answer() {
        return answer;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isCheated() {
        return cheated;
    }

    public void setCheated(boolean cheated) {
        this.cheated = cheated;
    }
}
