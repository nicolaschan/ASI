package com.nicolaschan.asi.algorithms;

/**
 * Created by nicol on 3/17/2017.
 */
public class AsymptoteScoreCounter extends ScoreCounter {

    // equation: counter = 100(1-1.02^(-x))

    float counter = 0;
    float max = 100;
    int count = 0;

    public AsymptoteScoreCounter() {

    }

    public void add() {
        count++;
    }

    public void sub() {
        count--;
    }

    public void add(int amount) {
        count += amount;
    }

    public float getValue() {
        return 100 * (1 - (float) Math.pow(1.02, count));
    }

}
