package com.nicolaschan.asi.music;

import com.nicolaschan.asi.algorithms.Evolvable;
import com.nicolaschan.asi.algorithms.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by nicolas on 1/10/17.
 */
public class Rhythm implements Evolvable {

    int scale;
    List<Integer> durations;

    public Rhythm(List<Integer> durations) {
        this(durations, 16);
    }

    public Rhythm(List<Integer> durations, int scale) {
        this.scale = scale;
        this.durations = durations;
    }
    public Rhythm(Genome genome) {
        List<Integer> durations = new ArrayList<>();
        for (int i = 0; i < genome.getSize(); i++)
            durations.set(i, genome.getValues()[i]);

        this.scale = scale;
        this.durations = durations;
    }

    public Genome getGenome() {
        int[] output = new int[durations.size()];
        for (int i = 0; i < durations.size(); i++)
            output[i] = durations.get(i);
        return new Genome(output);
    }

    public static Rhythm generateRandomRhythm(long seed, int length) {
        return generateRandomRhythm(seed, length, 8);
    }

    public static Rhythm generateRandomRhythm(long seed, int length, int scale) {
        Random random = new Random(seed);

        List<Integer> durations = new ArrayList<Integer>();

        int maximum = length * scale;
        int durationSoFar = 0;
        while (durationSoFar < maximum) {
            int duration = random.nextInt(scale) + 1;
            durationSoFar += duration;
            durations.add(duration);
        }

        int overshoot = durationSoFar - maximum;
        int lastIndex = durations.size() - 1;
        durations.set(lastIndex, durations.get(lastIndex) - overshoot);

        return new Rhythm(durations, scale);
    }

    public double[] getDurations() {
        double[] out = new double[durations.size()];

        for (int i = 0; i < durations.size(); i++) {
            out[i] = durations.get(i) / (((double) scale) / 4);
        }

        return out;
    }

    public int getNumberOfDurations() {
        return durations.size();
    }

}
