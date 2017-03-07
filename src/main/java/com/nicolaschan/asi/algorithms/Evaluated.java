package com.nicolaschan.asi.algorithms;

import java.util.Arrays;

/**
 * Created by nicol on 2/13/2017.
 */
public class Evaluated {

    private float score;
    private Genome object;

    public Evaluated(float score, Genome object) {
        this.score = score;
        this.object = object;
    }

    public float getScore() {
        return score;
    }

    public Genome getObject() {
        return object;
    }

    public String toString() {
        return "Eval " + Arrays.toString(object.getValues()) + "=" + score;
    }
}
