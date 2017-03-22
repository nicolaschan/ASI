package com.nicolaschan.asi.algorithms;

/**
 * Created by nicol on 3/19/2017.
 */
public abstract class ScoreCounter {

    private int count;

    public void add() {
        count++;
    }
    public void sub() {
        count--;
    }
    public void add(int i) {
        count += i;
    }
    public abstract float getValue();
}
