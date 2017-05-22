package com.nicolaschan.asi.music;

import com.nicolaschan.asi.algorithms.Genome;
import jm.music.data.Note;

import java.util.Random;

import static jm.constants.Pitches.C4;

/**
 * Created by nicolas on 1/9/17.
 */
public class Melody {

    private Rhythm rhythm;
    private int[] pitches;

    private static Rhythm removeZeros(Rhythm rhythm) {
        int[] values = rhythm.getGenome().getValues();
        int countZeros = 0;
        for (int val : values)
            if (val == 0)
                countZeros++;
        int[] newValues = new int[values.length - countZeros];
        int j = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != 0) {
                newValues[j] = values[i];
                j++;
            }
        }
        return new Rhythm(new Genome(newValues));
    }

    public Melody(Rhythm rhythm, int[] pitches) {
        rhythm = removeZeros(rhythm);
        this.rhythm = rhythm;
        this.pitches = pitches;
    }

    public static Melody generateRandomMelody(long seed, int length) {
        Random random = new Random(seed);
        long rhythmSeed = random.nextLong();
        long noteSeed = random.nextLong();

        Rhythm rhythm = Rhythm.generateRandomRhythm(rhythmSeed, length);
        int numberOfNotes = rhythm.getNumberOfDurations();
        int[] pitches = generateRandomPitchesUniform(noteSeed, numberOfNotes);

        return new Melody(rhythm, pitches);
    }

    private static int[] generateRandomPitchesUniform(long seed, int count) {
        return generateRandomPitchesUniform(seed, count, 10, C4);
    }

    private static int[] generateRandomPitchesUniform(long seed, int count, int maxJump, int startingNote) {
        Random random = new Random(seed);

        int[] pitches = new int[count];
        int currentPitch = startingNote;
        for (int i = 0; i < count; i++) {
            int pitch = currentPitch + random.nextInt(maxJump) - (maxJump / 2);
            pitches[i] = pitch;
            currentPitch = pitch;
        }

        return pitches;
    }

    private static int[] generateRandomPitchesGaussian(long seed, int count) {
        return generateRandomPitchesGaussian(seed, count, 2, C4);
    }

    public Rhythm getRhythm() {
        return rhythm;
    }

    public int[] getPitches() {
        return pitches;
    }

    private static int[] generateRandomPitchesGaussian(long seed, int count, int stdev, int startingNote) {
        Random random = new Random(seed);

        int[] pitches = new int[count];
        int currentPitch = startingNote;
        for (int i = 0; i < count; i++) {
            int pitch = currentPitch + (int) random.nextGaussian() * stdev;
            pitches[i] = pitch;

            currentPitch = pitch;
        }

        return pitches;
    }

    public Note[] getNotes() {
        int numberOfNotes = Math.min(pitches.length, rhythm.getDurations().length);
        Note[] notes = new Note[numberOfNotes];

        for (int i = 0; i < numberOfNotes; i++) {
            notes[i] = new Note(pitches[i], rhythm.getDurations()[i]);
        }

        return notes;
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        Note[] notes = getNotes();
        for (int i = 0; i < notes.length; i++) {
            out.append("(");
            out.append(notes[i].getPitch());
            out.append(',');
            out.append(notes[i].getDuration());
            out.append(") ");
        }
        return out.toString();
    }
}
