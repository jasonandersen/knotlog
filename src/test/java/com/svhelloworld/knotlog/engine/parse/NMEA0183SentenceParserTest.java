package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.svhelloworld.knotlog.messages.VesselMessages;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Test the {@link NMEA0183SentenceParser} class.
 */
public class NMEA0183SentenceParserTest extends BaseIntegrationTest {

    @Autowired
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
