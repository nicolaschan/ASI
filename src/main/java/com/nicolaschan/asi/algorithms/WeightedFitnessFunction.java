package com.nicolaschan.asi.algorithms;

/**
 * Created by nicol on 3/13/2017.
 */
public class WeightedFitnessFunction implements FitnessFunction {

    private int weight;
    private FitnessFunction function;

    public WeightedFitnessFunction(int weight, FitnessFunction function) {
        this.weight = weight;
        this.function = function;
    }

    public int getWeight() {
        return weight;
    }

    public FitnessFunction getFunction() {
        return function;
    }

    @Override
    public float call(Genome genome) {
        return function.call(genome);
    }
}
