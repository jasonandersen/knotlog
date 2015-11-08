package com.svhelloworld.knotlog.event;

import com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence;

/**
 * An NMEA0183 sentence was discovered, either through a real-time source or a 
 * file import.
 */
public class NMEA0183SentenceDiscovered {

    private final NMEA0183Sentence sentence;

    /**
     * Constructor allowing an NMEA0183 sentence from a string
     * @param sentence
     */
    public NMEA0183SentenceDiscovered(String sentence) {
        this(new NMEA0183Sentence(sentence));
    }

    /**
     * Constructor
     * @param sentence
     * @throws NullPointerException when sentence is null
     */
    public NMEA0183SentenceDiscovered(NMEA0183Sentence sentence) {
        if (sentence == null) {
            throw new IllegalArgumentException("sentence cannot be null");
        }
        this.sentence = sentence;
    }

    /**
     * @return the NMEA0183 sentence that was discovered, cannot return null
     */
    public NMEA0183Sentence getSentence() {
        return sentence;
    }

}
