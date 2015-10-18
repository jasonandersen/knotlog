package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.svhelloworld.knotlog.messages.VesselMessages;

/**
 * Test the {@link NMEA0183SentenceParser} class.
 */
public class NMEA0183SentenceParserTest {

    private NMEA0183SentenceParser parser = new NMEA0183SentenceParser();

    @Test
    public void test() {
        VesselMessages messages = parser.parseSentence("$IIDBT,,,,");
        assertFalse(messages.getUnrecognizedMessages().isEmpty());
    }
}
