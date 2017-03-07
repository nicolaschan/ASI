package com.nicolaschan.asi.music;

import jm.music.data.Note;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by nicolas on 1/9/17.
 */
public class Chord {
    private RomanNumeral romanNumeral;
    private Tonality tonality;

    public Chord(RomanNumeral romanNumeral, Tonality tonality) {
        this.romanNumeral = romanNumeral;
        this.tonality = tonality;
    }

    public Note[] getNotes() {
        throw new NotImplementedException();
    }

    public static Chord getRandomChord() {
        throw new NotImplementedException();
    }
}
