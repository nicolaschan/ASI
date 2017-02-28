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

    public String toString() {
        StringBuilder out = new StringBuilder("Genome [");

        for (int i = 0; i < values.length; i++)
            out.append((i == values.length - 1) ? values[i] : values[i] + ",");

        return out + "]";
    }
}
