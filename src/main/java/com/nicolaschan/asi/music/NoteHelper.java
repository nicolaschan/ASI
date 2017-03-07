package com.nicolaschan.asi.music;

import jm.music.data.Note;

/**
 * Created by nicol on 1/22/2017.
 */
public class NoteHelper {
    public static int noteToValue(String s) {
        switch (s) {
            case "C":
                return 0;
            case "C#":
                return 1;
            case "D":
                return 2;
            case "D#":
                return 3;
            case "E":
                return 4;
            case "F":
                return 5;
            case "F#":
                return 6;
            case "G":
                return 7;
            case "G#":
                return 8;
            case "A":
                return 9;
            case "A#":
                return 10;
            case "B":
                return 11;
            default:
                return -1;
        }
    }

    public static String noteToString(int i) {
        i = i % 12;
        return (i > 0) ? Note.getNote(i) : Note.getNote(12 + i);
    }
}
