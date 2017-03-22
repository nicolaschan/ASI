package com.nicolaschan.asi.algorithms;

import java.util.Arrays;

/**
 * Created by nicol on 3/13/2017.
 */
public class RhythmFitnessFunction extends WeightedFitnessFunctions {

    public FitnessFunction rhythmVariety;
    public FitnessFunction preferStrongBeats;

    public RhythmFitnessFunction() {
        rhythmVariety = new FitnessFunction() {
            // calculated by adding distances from the median
            @Override
            public float call(Genome genome) {
                int deltas = 0;
                int median = median(genome.getValues());
                for (int i = 0; i < genome.getSize(); i++) {
                    deltas += Math.abs(genome.getValues()[i] - median);
                }
                return deltas;
            }

            private int median(int[] nums) {
                int[] numsClone = nums.clone();
                Arrays.sort(numsClone);
                return numsClone[numsClone.length / 2];
            }
        };
        preferStrongBeats = new FitnessFunction() {
            // assuming numbers are 16th notes
            private int scale = 16;

            @Override
            public float call(Genome genome) {

                // +1 score for every note that begins on a strong beat in 4/4 time
                int score = 0;
                // keep track of the current place
                int place = 0;

                for (int i = 0; i < genome.getSize(); i++) {
                    score += (isStrongBeat(place)) ? 1 : 0;
                    place += genome.getValues()[i];
                }

                return score;
            }

            private boolean isStrongBeat(int place) {
                return (place % (scale)) == 0;
            }
        };
        WeightedFitnessFunction weightedRhythmVariety = new WeightedFitnessFunction(1, rhythmVariety);
        WeightedFitnessFunction weightedPreferStrongBeats = new WeightedFitnessFunction(10, preferStrongBeats);

        super.addFunction(weightedPreferStrongBeats, weightedRhythmVariety);
    }


}
