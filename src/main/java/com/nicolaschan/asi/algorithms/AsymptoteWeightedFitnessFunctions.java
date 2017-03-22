package com.nicolaschan.asi.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicol on 3/20/2017.
 */
public class AsymptoteWeightedFitnessFunctions implements FitnessFunction {

    private List<WeightedFitnessFunction> functions = new ArrayList<WeightedFitnessFunction>();

    @Override
    // using equation: score = 100 ( 1 - 0.98^(-x) )
    public float call(Genome genome) {
        float score = 0;
        for (int i = 0; i < functions.size(); i++) {
            score += 100 * (1 - Math.pow(0.98, (functions.get(i).getWeight() * functions.get(i).call(genome))));
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
