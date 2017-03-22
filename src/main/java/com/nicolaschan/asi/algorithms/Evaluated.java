package com.nicolaschan.asi.algorithms;

import com.nicolaschan.asi.evaluate.Evaluator;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by nicol on 2/13/2017.
 */
public class Evaluated implements Comparable {

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

    public static float avg(Evaluated[] evaluateds) {
        float totalScore = 0;
        for (int i = 0; i < evaluateds.length; i++)
            totalScore += evaluateds[i].getScore();
        return totalScore / evaluateds.length;
    }

    @Override
    public int compareTo(Object o) {
        Evaluated e1 = this;
        Evaluated e2 = (Evaluated) o;
        return (int) Math.signum(e1.getScore() - e2.getScore());
    }
}
