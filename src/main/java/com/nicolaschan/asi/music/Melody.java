package com.nicolaschan.asi.music;

import jm.music.data.Note;

import java.util.Random;

import static jm.constants.Pitches.C4;

/**
 * Created by nicolas on 1/9/17.
 */
public class Melody {

    private Rhythm rhythm;
    private int[] pitches;

    public Melody(Rhythm rhythm, int[] pitches) {
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
        Note[] notes = new Note[pitches.length];

        for (int i = 0; i < pitches.length; i++) {
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
