package com.nhnacademy.brickbreaker;

public class GameState {

    private int score = 0;

    public int getScore() {
        return score;
    }

    public void updateScore() {
        score++;
    }
}
