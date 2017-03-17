package com.nicolaschan.asi.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicol on 3/13/2017.
 */
public class WeightedFitnessFunctions implements FitnessFunction {

    private List<WeightedFitnessFunction> functions = new ArrayList<WeightedFitnessFunction>();

    @Override
    public float call(Genome genome) {
        float score = 0;
        for (int i = 0; i < functions.size(); i++) {
            score += functions.get(i).getWeight() * functions.get(i).call(genome);
        }
        return score;
    }

    protected void addFunction(WeightedFitnessFunction function) {
        functions.add(function);
    }
    protected void addFunction(WeightedFitnessFunction... functions) {
        for (int i = 0; i < functions.length; i++) {
            this.functions.add(functions[i]);
        }
    }
}
