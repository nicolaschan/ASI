package com.nicolaschan.asi.main;

import com.explodingart.jmusic.instrument.SimpleSineInst;
import com.nicolaschan.asi.algorithms.BasicGeneticAlgorithm;
import com.nicolaschan.asi.algorithms.Evolvable;
import com.nicolaschan.asi.algorithms.FitnessFunction;
import com.nicolaschan.asi.algorithms.Genome;
import com.nicolaschan.asi.music.Melody;
import com.nicolaschan.asi.music.Rhythm;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
import jm.util.Write;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nicolas on 1/4/17.
 */
public class ASIMain {

    public static void main(String[] args) throws IOException {
        evolveRhythm();
    }

    public static void evolveRhythm() {
        BasicGeneticAlgorithm alg = new BasicGeneticAlgorithm();

        Genome[] genomes = Genome.generateRandomGenomes(0, 5);
        System.out.println(Arrays.toString(genomes));

        FitnessFunction fitnessFunction = new FitnessFunction() {
            @Override
            public float call(Genome genome) {
                return sum(genome);
            }

            private int countOnes(Genome genome) {
                int total = 0;
                for (int i : genome.getValues())
                    total += (i == 1) ? 1 : 0;
                return total;
            }

            private int sum(Genome genome) {
                int total = 0;
                for (int i = 0; i < genome.getValues().length; i++) {
                    total += genome.getValues()[i];
                }
                return total;
            }
        };
        Genome[] output = alg.evolve(genomes, fitnessFunction, 5);

        System.out.println(Arrays.toString(output));
        System.out.println(Arrays.toString(alg.score(fitnessFunction, output)));
    }

    public static void randomMelody() {
        int seed = 2;
        Melody melody = Melody.generateRandomMelody(seed, 8);

        Score score = new Score("Random (Seed: " + seed + ")");
        Part part = new Part("Piano", 0, 9);

        Phrase phr = new Phrase();
        Note[] notes = melody.getNotes();

        for (int i = 0; i < notes.length; i++) {
            phr.add(notes[i]);
        }

        part.add(phr);
        score.add(part);

        System.out.println(melody);

        Instrument instrument = new SimpleSineInst(44100);

        View.show(score);
        Write.au(score, "out.au", instrument);
        Write.midi(score, "out2.mid");


    }

}
