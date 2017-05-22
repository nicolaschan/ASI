package com.nicolaschan.asi.algorithms;

import com.nicolaschan.asi.evaluate.ToneEvaluator;

/**
 * Created by nicol on 3/17/2017.
 */
public class PitchFitnessFunction extends WeightedFitnessFunctions {

    public FitnessFunction pitchDiversity;
    public FitnessFunction notesInKey;
    public FitnessFunction reasonableRange;

    public PitchFitnessFunction() {
        reasonableRange = new FitnessFunction() {
            @Override
            public float call(Genome genome) {
                int score = 0;
                for (int i = 0; i < genome.getValues().length; i++) {
                    score -= Math.abs(genome.getValues()[i] - 60);
                }
                return score;
            }
        };
        pitchDiversity = new FitnessFunction() {
            @Override
            public float call(Genome genome) {
                int score = 0;

                for (int i = 1; i < genome.getValues().length; i++)
                    if ((genome.getValues()[i - 1] % 12) != (genome.getValues()[i] % 12))
                        score++;

                return score;
            }
        };
        notesInKey = new FitnessFunction() {
            // TODO: add support for both major and minor
            // only major right now

            //  W W H W W W H
            // 0 2 2 1 2 2 2 1
            // 0 2 4 5 7 9 11 12
            int[] acceptableDistances = new int[]{-12,-10,-8,-7,-5,-3,-1,0,2,4,5,7,9,11};

            private boolean isInKey(int startingNote, int testingNote) {
                int distance = (testingNote - startingNote) % 12;

                for (int i = 0; i < acceptableDistances.length; i++) {
                    if (distance == acceptableDistances[i])
                        return true;
                }

                return false;
            }

            /*
            Old implementation, was incorrect
            private boolean isInKey(int startingNote, int testingNote) {
                if (testingNote < startingNote)
                    return isInKey(testingNote, startingNote);
                int distance = (startingNote - testingNote) % 12;

                for (int i = 0; i < acceptableDistances.length; i++) {
                    if (distance == acceptableDistances[i])
                        return true;
                }
                return false;
            }*/

            @Override
            public float call(Genome genome) {
                int key = genome.getValues()[0];
                int score = 0;
                for (int i = 1; i < genome.getValues().length; i++) {
                    if (isInKey(key, genome.getValues()[i]))
                        score++;
                }
                return score;
            }
        };
        WeightedFitnessFunction weightedPitchDiversity = new WeightedFitnessFunction(1000, pitchDiversity);
        WeightedFitnessFunction weightedNotesInKey = new WeightedFitnessFunction(1000, notesInKey);
        WeightedFitnessFunction weightedReasonableRange = new WeightedFitnessFunction(1, reasonableRange);

        super.addFunction(weightedPitchDiversity, weightedNotesInKey, weightedReasonableRange);
    }

}
