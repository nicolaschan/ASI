package com.nicolaschan.asi.algorithms;

import com.nicolaschan.asi.evaluate.Evaluator;
import com.sun.xml.internal.ws.policy.spi.PolicyAssertionValidator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by nicolas on 1/12/17.
 */
public class BasicGeneticAlgorithm extends Algorithm {

    public Genome[] evolve(Genome[] genomes, FitnessFunction fitnessFunction) {
        return evolve(genomes, fitnessFunction, 1);
    }

    public Genome[] evolve(Genome[] genomes, FitnessFunction fitnessFunction, int generationsRemaining) {
        return evolve(genomes, fitnessFunction, generationsRemaining, 0F);
    }

    public Genome[] evolve(Genome[] genomes, FitnessFunction fitnessFunction, int generationsRemaining, float mutationProbability) {
        return evolve(genomes, fitnessFunction, generationsRemaining, mutationProbability, 4);
    }

    public Genome[] evolve(Genome[] genomes, FitnessFunction fitnessFunction, int generationsRemaining, float mutationProbability, int numberOfTopScores) {
        return evolve(genomes, fitnessFunction, generationsRemaining, mutationProbability, numberOfTopScores, false);
    }
    public Genome[] evolve(Genome[] genomes, FitnessFunction fitnessFunction, int generationsRemaining, float mutationProbability, int numberOfTopScores, boolean positiveValuesOnly) {
        return evolve(genomes, fitnessFunction, generationsRemaining, mutationProbability, numberOfTopScores, positiveValuesOnly, 0);
    }

    public Genome[] evolve(Genome[] genomes, FitnessFunction fitnessFunction, int generationsRemaining, float mutationProbability, int numberOfTopScores, boolean positiveValuesOnly, long seed) {
        return evolve(genomes, fitnessFunction, generationsRemaining, mutationProbability, numberOfTopScores, positiveValuesOnly, new Random(seed));
    }

    public static void insertInEvaluatedList(List<Evaluated> evaluateds, Evaluated evaluated) {
        for (int i = 0; i < evaluateds.size(); i++) {
            if (evaluateds.get(i).getScore() < evaluated.getScore())
                continue;
            evaluateds.add(i, evaluated);
            return;
        }
        evaluateds.add(evaluated);
    }

    private float averageScore(Genome[] genomes, FitnessFunction fitnessFunction) {
        float total = 0;
        for (int i = 0; i < genomes.length; i++) {
            total += fitnessFunction.call(genomes[i]);
        }
        return total / genomes.length;
    }

    public Genome[] evolve(Genome[] genomes, FitnessFunction fitnessFunction, int generationsRemaining, float mutationProbability, int numberOfTopScores, boolean positiveValuesOnly, Random random) {
        System.out.println(generationsRemaining + "\t" + averageScore(genomes, fitnessFunction));

        if (generationsRemaining == 0)
            return genomes;

        List<Evaluated> evaluateds = new ArrayList<Evaluated>();

        for (int i = 0; i < genomes.length; i++) {
            Evaluated evaluated = new Evaluated(fitnessFunction.call(genomes[i]), genomes[i]);
            insertInEvaluatedList(evaluateds, evaluated);
        }

        Genome[] topScores = new Genome[numberOfTopScores];
        for (int i = 0; i < topScores.length; i++) {
            topScores[i] = evaluateds.get(evaluateds.size() - 1 - i).getObject();
        }

        Genome[] topGenomes = new Genome[numberOfTopScores];
        for (int i = 0; i < topGenomes.length; i++) {
            topGenomes[i] = topScores[i];
        }

        Genome[] nextGeneration = new Genome[genomes.length];
        for (int i = 0; i < nextGeneration.length; i++) {
            // choose random two from top
            int random1 = (int) (random.nextFloat() * topGenomes.length);
            int random2 = (int) (random.nextFloat() * topGenomes.length);
            // perform crossover operation
            nextGeneration[i] = mutate(crossover(topGenomes[random1], topGenomes[random2], random), mutationProbability, random, positiveValuesOnly);
        }

        //if (generationsRemaining == 1)
        //    return nextGeneration;
        return evolve(nextGeneration, fitnessFunction, generationsRemaining - 1, mutationProbability, numberOfTopScores, positiveValuesOnly, random);
    }

    public Evaluated[] score(FitnessFunction function, Genome... genomes) {
        Evaluated[] output = new Evaluated[genomes.length];
        for (int i = 0; i < genomes.length; i++) {
            output[i] = new Evaluated(function.call(genomes[i]), genomes[i]);
        }
        return output;
    }

    private Genome crossover(Genome genome1, Genome genome2, Random random) {
        int maxPoint = Math.min(genome1.getSize(), genome2.getSize());
        int point = (int) (random.nextDouble() * maxPoint);
        return crossover(genome1, genome2, point);
    }

    private Genome crossover(Genome genome1, Genome genome2, int point) {
        if (point > genome1.getSize() - 1 || point > genome2.getSize() - 1)
            throw new IndexOutOfBoundsException();

        int[] output = new int[Math.min(genome1.getSize(), genome2.getSize())];

        for (int i = 0; i < output.length; i++) {
            output[i] = (i < point) ? genome1.getValues()[i] : genome2.getValues()[i];
        }

        return new Genome(output);
    }

    private Genome mutate(Genome genome, float probability, Random random, boolean positiveValuesOnly) {
        for (int i = 0; i < genome.getSize(); i++) {
            boolean shouldMutate = random.nextDouble() < probability;
            if (shouldMutate) {
                double randomDouble = random.nextDouble();
                int randomChange = (int) Math.signum(randomDouble - 0.5);

                int newValue = genome.getValues()[i] + randomChange;
                if (positiveValuesOnly && newValue < 0)
                    newValue = 1;
                genome.getValues()[i] = newValue;
            }
        }
        return genome;
    }

    private Genome[] mutateMany(float probability, Random random, boolean positiveValuesOnly, Genome... genomes) {
        for (int i = 0; i < genomes.length; i++)
            mutate(genomes[i], probability, random, positiveValuesOnly);
        return genomes;
    }

    private Genome[] combineTwo(Genome... genomes) {

        Genome[] output = new Genome[genomes.length - 1];

        for (int i = 0; i < genomes.length - 1; i++) {
            output[i] = combine(genomes[i], genomes[i + 1]);
        }

        return output;

    }

    private Genome combine(Genome... genomes) {
        int[] output = new int[genomes[0].getValues().length];

        for (int i = 0; i < genomes.length; i++) {
            for (int j = 0; i < output.length; i++) {
                output[j] += genomes[i].getValues()[j];
            }
        }

        for (int i = 0; i < output.length; i++) {
            output[i] /= genomes.length;
        }

        return new Genome(output);
    }


}
