package com.nicolaschan.asi.evaluate;

import com.nicolaschan.asi.music.Melody;
import jm.music.data.Note;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * Created by nicol on 2/9/2017.
 */
public class ToneEvaluator extends Evaluator {

    public float notesInKey(Melody melody) {
        throw new NotImplementedException();
    }

    public float notesInKey(Melody melody, int key, boolean major) {
        float score = 0F;

        for (int i = 0; i < melody.getNotes().length; i++)
            score += noteIsInKey(melody.getNotes()[i].getPitch(), key, major) ? 1 : 0;

        return score / melody.getNotes().length;
    }

    private boolean noteIsInKey(int note, int key, boolean major) {
        return major ? noteIsInKeyMajor(note, key) : noteIsInKeyNaturalMinor(note, key);
    }
    private boolean noteIsInKeyMajor(int note, int key) {
        // W, W, H, W, W, W, H
        // 0, 2, 1, 2, 2, 2, 1
        // 0, 2, 3, 5, 7, 9, 10
        String testingNoteName = new Note(note, 0).getNote();
        int[] notesInKeyInt = new int[]{
            key, key + 2, key + 3, key + 5, key + 7, key + 9, key + 10
        };
        for (int i = 0; i < notesInKeyInt.length; i++) {
            String currentNoteName = new Note(notesInKeyInt[i], 0).getNote();
            if (currentNoteName.equals(testingNoteName))
                return true;
        }
        return false;
    }
    private boolean noteIsInKeyNaturalMinor(int note, int key) {
        // W, H, W, W, H, W, W
        // 0, 1, 2, 2, 1, 2, 2
        // 0, 1, 3, 5, 6, 8, 10
        String testingNoteName = new Note(note, 0).getNote();
        int[] notesInKeyInt = new int[]{
          key, key + 1, key + 3, key + 5, key + 6, key + 8, key + 10
        };
        for (int i = 0; i < notesInKeyInt.length; i++) {
            String currentNoteName = new Note(notesInKeyInt[i], 0).getNote();
            if (currentNoteName.equals(testingNoteName))
                return true;
        }
        return false;
    }

    public float pitchDiversity(Melody melody) {
        throw new NotImplementedException();
    }

    public float pitchesWithinReasonableRange(Melody melody) {
        throw new NotImplementedException();
    }

}
