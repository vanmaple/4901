package com.example.a4501assignment;

public class ApiData {
    private int left;
    private int right;
    private int guess;

    public ApiData(int left, int right, int guess) {
        this.left = left;
        this.right = right;
        this.guess = guess;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    @Override
    public String toString() {
        return "Left: " + left + ", Right: " + right + ", Guess: " + guess;
    }
}
