package com.nicolaschan.asi.algorithms;

import java.util.Random;

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

    public static Genome generateRandomGenome(long seed) {
        return generateRandomGenome(seed, 8);
    }

    public static Genome generateRandomGenome(long seed, int size) {
        return generateRandomGenome(seed, size, 0, 8);
    }

    public static Genome generateRandomGenome(long seed, int size, int lowerBound, int upperBound) {
        Random random = new Random(seed);
        int[] output = new int[size];

        for (int i = 0; i < size; i++) {
            output[i] = lowerBound + (int) (random.nextDouble() * (upperBound - lowerBound));
        }

        return new Genome(output);
    }

    public static Genome[] generateRandomGenomes(long seed, int size) {
        Genome[] genomes = new Genome[size];
        Random random = new Random(seed);
        for (int i = 0; i < size; i++) {
            genomes[i] = Genome.generateRandomGenome(random.nextInt());
        }
        return genomes;
    }
}
