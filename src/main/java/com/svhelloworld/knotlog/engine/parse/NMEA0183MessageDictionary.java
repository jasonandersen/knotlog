package com.svhelloworld.knotlog.engine.parse;

import org.springframework.stereotype.Component;

/**
 * Dictionary that describes NMEA0183 sentences.
 */
@Component
public class NMEA0183MessageDictionary extends CSVMessageDictionary {

    /**
     * Path to CSV message dictionary
     */
    private static final String DICTIONARY_PATH = "com/svhelloworld/knotlog/engine/parse/NMEA0183MessageDictionary.csv";

    /**
     * Constructor.
     */
    public NMEA0183MessageDictionary() {
        super.initialize(DICTIONARY_PATH);
    }

}
