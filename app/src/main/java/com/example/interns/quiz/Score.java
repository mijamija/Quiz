package com.example.interns.quiz;

import java.io.Serializable;

/**
 * Created by Interns on 3/30/2017.
 */

public class Score implements Serializable{

    private String player;
    private int score;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
