package com.nicolaschan.asi.algorithms;

/**
 * Created by nicol on 2/16/2017.
 */
public class Genome {

    int[] values;

    public Genome(int[] values) {
        this.values = values;
    }

    public int[] getValues() {
        return values;
    }

    public int getSize() {
        return values.length;
    }
}
