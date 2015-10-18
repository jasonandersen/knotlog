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

    @Test
    public void testTKW() {
        VesselMessages messages = parser.parseSentence("54059,V,SRL,PTTKW,4,P,2,B,0,,,,,,,,,,,,,,,");
        assertFalse(messages.getUnrecognizedMessages().isEmpty());
    }
}
