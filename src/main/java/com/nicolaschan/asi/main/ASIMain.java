package com.nicolaschan.asi.main;

import com.explodingart.jmusic.instrument.SimpleSineInst;
import com.nicolaschan.asi.algorithms.*;
import com.nicolaschan.asi.music.Melody;
import com.nicolaschan.asi.music.Rhythm;
import jm.JMC;
import jm.audio.Instrument;
import jm.constants.ProgramChanges;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.ga.PhrGeneticAlgorithm;
import jm.util.View;
import jm.util.Write;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jm.constants.Durations.QUARTER_NOTE;
import static jm.constants.Pitches.C4;

/**
 * Created by nicolas on 1/4/17.
 */
public class ASIMain {

    public static void main(String[] args) throws IOException {
        //createTestMidi();
        //evolveRhythm();
        evolvePitches();
        //randomMelody();
    }

    public static void randomMelody() {
        int seed = 5;
        Melody melody = Melody.generateRandomMelody(seed, 8);
        writeMelody(melody, "Random (seed: " + seed + ")", "random-seed" + seed);
    }

    public static void writeMelody(Melody melody, String name, String fileName) {
        Score s = new Score(name);
        Part p = new Part("Piano", ProgramChanges.PIANO, 0);
        Phrase phr = new Phrase("Phrase", 0.0);

        Note[] notes = melody.getNotes();
        Note n = new Note(56, 3.5);
        System.out.println(n);
        for (int i = 0; i < notes.length; i++) {
            phr.add(notes[i]);
        }

        p.addPhrase(phr);
        s.addPart(p);

        Write.midi(s, "outputs/" + fileName + ".mid");

        Instrument instrument = new SimpleSineInst(44100);

        View.show(s);
        Write.au(s, "outputs/" + fileName + ".au", instrument);
    }

    public static void evolveRhythm() {
        BasicGeneticAlgorithm alg = new BasicGeneticAlgorithm();

        int size = 5000;
        Genome[] rhythms = new Genome[size];
        for (int i = 0; i < rhythms.length; i++) {
            rhythms[i] = Rhythm.generateRandomRhythm(i, 8).getGenome();
        }
        FitnessFunction fitnessFunction = new RhythmFitnessFunction().preferStrongBeats;

        Evaluated[] evaluatedRhythms = alg.score(fitnessFunction, rhythms);
        Arrays.sort(evaluatedRhythms);
        System.out.println("Initial population: " + Arrays.toString(evaluatedRhythms));

        Genome[] output = alg.evolve(rhythms, fitnessFunction, 100, 0.01F,300, true);

        Rhythm[] outputRhythms = new Rhythm[output.length];
        for (int i = 0; i < output.length; i++) {
            outputRhythms[i] = new Rhythm(output[i]);
        }

        Evaluated[] evaluatedOutput = alg.score(fitnessFunction, output);
        Arrays.sort(evaluatedOutput);
        System.out.println(Arrays.toString(evaluatedOutput));

        int[] pitchesAllC = new int[outputRhythms.length]; // TODO: see if this array length is correct
        for (int i = 0; i < pitchesAllC.length; i++)
            pitchesAllC[i] = 60;


        Melody melodyOrig = new Melody(new Rhythm(rhythms[0]), pitchesAllC);
        writeMelody(melodyOrig, "Original Rhythm", "original-rhythm");
        Melody melodyEvolved = new Melody(outputRhythms[0], pitchesAllC);
        writeMelody(melodyEvolved, "Evolved Rhythm", "evolved-rhythm");
    }

    public static void evolvePitches() {
        BasicGeneticAlgorithm alg = new BasicGeneticAlgorithm();

        int size = 5000;
        Genome[] pitches = new Genome[size];
        for (int i = 0; i < pitches.length; i++) {
            pitches[i] = new Genome(Melody.generateRandomMelody(i, 8).getPitches());
        }
        FitnessFunction fitnessFunction = new PitchFitnessFunction();

        Evaluated[] evaluatedPitches = alg.score(fitnessFunction, pitches);
        Arrays.sort(evaluatedPitches);
        System.out.println("Initial population: " + Arrays.toString(evaluatedPitches));

        Genome[] output = alg.evolve(pitches, fitnessFunction, 100, 0.01F,300, true);

        int[][] outputPitches = new int[output.length][];
        for (int i = 0; i < output.length; i++) {
            outputPitches[i] = output[i].getValues();
        }

        Evaluated[] evaluatedKey = alg.score(new PitchFitnessFunction().notesInKey, output);
        Arrays.sort(evaluatedKey);
        System.out.println("key: " + Evaluated.avg(evaluatedKey));
        Evaluated[] evaluatedPitchDiversity = alg.score(new PitchFitnessFunction().pitchDiversity, output);
        Arrays.sort(evaluatedPitchDiversity);
        System.out.println("pitch diversity: " + Evaluated.avg(evaluatedPitchDiversity));

        Evaluated[] evaluatedOutput = alg.score(fitnessFunction, output);
        Arrays.sort(evaluatedOutput);
        System.out.println(Arrays.toString(evaluatedOutput));

        List<Integer> rhythmAll1 = new ArrayList<Integer>();
        for (int i = 0; i < outputPitches[0].length; i++)
            rhythmAll1.add(4);
        Rhythm rhythm = new Rhythm(rhythmAll1);

        Melody melodyOrig = new Melody(rhythm, pitches[0].getValues());
        writeMelody(melodyOrig, "Original Pitches", "original-pitches");
        Melody melodyEvolved = new Melody(rhythm, outputPitches[outputPitches.length - 1]);
        writeMelody(melodyEvolved, "Evolved Pitches", "evolved-asymptote-pitches");
    }

    public static void evolveRandom() {
        BasicGeneticAlgorithm alg = new BasicGeneticAlgorithm();

        Genome[] genomes = Genome.generateRandomGenomes(0, 20);
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
        Genome[] output = alg.evolve(genomes, fitnessFunction, 5, 1F, 4);

        System.out.println(Arrays.toString(output));
        System.out.println(Arrays.toString(alg.score(fitnessFunction, output)));
    }

    public static void randomMelodyObsolete() {
        int seed = 2;
        Melody melody = Melody.generateRandomMelody(seed, 8);

        /*Score score = new Score("Random (Seed: " + seed + ")");
        Part part = new Part("Piano", ProgramChanges.PIANO, 0);
        Phrase phr = new Phrase("Random Phrase", 0.0);

        Note[] notes = melody.getNotes();

        notes[0] = new Note(56, 3.5);

        for (int i = 0; i < notes.length; i++) {
            System.out.println(notes[i]);
            phr.add(notes[i]);
        }

        part.add(phr);
        score.add(part);*/

        System.out.println(melody);

        Score s = new Score("Test Midi");
        Part p = new Part("Piano", ProgramChanges.PIANO, 0);
        Phrase phr = new Phrase("Test Phrase", 0.0);

        Note[] notes = melody.getNotes();
        Note n = new Note(56, 3.5);
        phr.add(n);
//        for (int i = 0; i < 1/*notes.length*/; i++) {
//            phr.add(notes[i]);
//        }

        System.out.println(phr.getSize());
        p.addPhrase(phr);
        s.addPart(p);

        Write.midi(s, "out1.mid");

        /*
        Write.midi(score, "out1.mid");

        Instrument instrument = new SimpleSineInst(44100);

        View.show(score);
        Write.au(score, "out.au", instrument);*/



    }

}
