package com.nicolaschan.asi.algorithms;

import java.util.Arrays;

/**
 * Created by nicol on 3/13/2017.
 */
public class RhythmFitnessFunction extends WeightedFitnessFunctions {

    public FitnessFunction rhythmVariety;
    public FitnessFunction preferStrongBeats;
    public FitnessFunction preferShorterNotes;

    public RhythmFitnessFunction() {
        preferShorterNotes = new FitnessFunction() {

            @Override
            public float call(Genome genome) {
                int score = 0;
                // for every note, give 2 points for 8th note, 1 point for quarter note
                for (int val : genome.getValues()) {
                    if (val == 2)
                        score += 2;
                    if (val == 4)
                        score += 1;
                }

                return score;
            }
        };
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
        //WeightedFitnessFunction weightedRhythmVariety = new WeightedFitnessFunction(10, rhythmVariety);
        WeightedFitnessFunction weightedPreferStrongBeats = new WeightedFitnessFunction(5, preferStrongBeats);
        WeightedFitnessFunction weightedPreferShorterNotes = new WeightedFitnessFunction(1, preferShorterNotes);

        super.addFunction(weightedPreferStrongBeats, /*weightedRhythmVariety,*/ weightedPreferShorterNotes);
    }


}
