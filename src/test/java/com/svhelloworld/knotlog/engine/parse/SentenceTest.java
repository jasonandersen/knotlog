package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence;

/**
 * Unit test for <tt>Sentence</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 11, 2010
 *
 */
public class SentenceTest {

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testEmptyLine() {
        /*
         * empty line
         */
        testCase("", 
                -1,
                "",
                "",
                "",
                NMEA0183Sentence.Validity.MALFORMED_TAG);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testValidTSTagOneFieldChecksum() {
        /*
         * valid timestamp first field
         * tag second field
         * valid tag found and 1 field
         * checksum valid
         */
        testCase("124098409893,$IIMWV,monkey*8B", 
                124098409893L,
                "II",
                "MWV",
                "8B",
                NMEA0183Sentence.Validity.VALID,
                "monkey");
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testNoTSValidTagChecksumNoFields() {
        /*
         * no timestamp in the first field
         * tag in the first field
         * tag found and no fields found
         * checksum valid
         */
        testCase("$GPGGA*9A", 
                -1,
                "GP",
                "GGA",
                "9A",
                NMEA0183Sentence.Validity.VALID);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testValidTSTagNoFieldsNoChecksum() {
        /* 
         * valid timestamp
         * tag in last field
         * no checksum
         */
        testCase("6582901,$IIDPT", 
                6582901,
                "II",
                "DPT",
                "",
                NMEA0183Sentence.Validity.MALFORMED_CHECKSUM);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testTagLowerCase() {

        /*
         * tag not found - lower case
         */
        testCase("1240984138,$gpgga,monkey*9A", 
                1240984138L,
                "",
                "",
                "",
                NMEA0183Sentence.Validity.MALFORMED_TAG,
                "1240984138",
                "$gpgga",
                "monkey",
                "9A");
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testTagNonCharacters() {
        /*
         * tag not found - non-characters
         */
        testCase("1249340127,$GP123,monkey*9A", 
                1249340127,
                "",
                "",
                "",
                NMEA0183Sentence.Validity.MALFORMED_TAG,
                "1249340127",
                "$GP123",
                "monkey",
                "9A");
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testTagNo$NFieldsEmptyStrings() {

        /*
         * tag without $
         * tag found and n fields found
         * fields with empty strings
         */
        testCase("14892301,GPGRS,024603.00,1,-1.8,-2.7,0.3,,,,,,,,,*6C", 
                14892301,
                "GP",
                "GRS",
                "6C",
                NMEA0183Sentence.Validity.VALID,
                "024603.00",
                "1",
                "-1.8",
                "-2.7",
                "0.3",
                "", "", "", "", "", "", "", "", "");
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testFieldsFoundInvalidChecksum() {
        /*
         * timestamp valid
         * tag valid
         * fields found
         * invalid checksum
         */
        testCase("124098409893,$IIMWV,monkey,foo*8G", 
                124098409893L,
                "II",
                "MWV",
                "",
                NMEA0183Sentence.Validity.MALFORMED_CHECKSUM,
                "monkey",
                "foo");
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testNoFieldsFoundInvalidChecksum() {
        /*
         * timestamp valid
         * tag valid
         * fields not found
         * invalid checksum
         */
        testCase("124098409893,$IIMWV*8G", 
                124098409893L,
                "II",
                "MWV",
                "",
                NMEA0183Sentence.Validity.MALFORMED_CHECKSUM);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testNegativeTimestampValidTagFields() {

        /*
         * negative timestamp
         * tag found and n fields found
         * fields with empty strings
         */
        testCase("-14892301,$GPGRS,024603.00,1,-1.8,-2.7,0.3*6C", 
                -1,
                "GP",
                "GRS",
                "6C",
                NMEA0183Sentence.Validity.VALID,
                "024603.00",
                "1",
                "-1.8",
                "-2.7",
                "0.3");
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testRSABug20100312() {
        /*
         * 3/12/2010
         * Found this bug while testing Garmin diagnostics file. This 
         * sentence should have four fields but it is only yielding two.
         * 25712461,V,SRL,AGRSA,00,A,,
         */
        NMEA0183Sentence sentence = new NMEA0183Sentence("25712461,V,SRL,AGRSA,00,A,,");
        assertEquals(4, sentence.getFields().size());
    }
    
    
    
    
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testGarminDiagSentence() {
        testCase("66940,V,GPS,GPRMC,171056,A,2645.4062,N,11153.6195,W,0.1,0,90210,10.8,E",
                66940,
                "GP",
                "RMC",
                "",
                NMEA0183Sentence.Validity.MALFORMED_CHECKSUM,
                "171056",
                "A",
                "2645.4062",
                "N",
                "11153.6195",
                "W",
                "0.1",
                "0",
                "90210",
                "10.8",
                "E");
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence#Sentence(java.lang.String)}.
     */
    @Test
    public void testSentenceNullString() {
        try {
            @SuppressWarnings("unused")
            NMEA0183Sentence sentence = new NMEA0183Sentence(null);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }
    
    
    
    /**
     * Runs a single test case.
     * @param line sentence line to test
     * @param timestamp expected timestamp
     * @param talkerId expected talkerId
     * @param tag expected tag
     * @param checksum expected checksum
     * @param validity expected validity
     * @param fields expected fields
     */
    private void testCase(
            String line,
            long timestamp,
            String talkerId,
            String tag,
            String checksum,
            NMEA0183Sentence.Validity validity,
            String... fields) {
        
        NMEA0183Sentence sentence = new NMEA0183Sentence(line);
        
        //check sentence members
        assertEquals(line, validity, sentence.getValidity());
        assertEquals(line, talkerId, sentence.getTalkerId());
        assertEquals(line, tag, sentence.getTag());
        assertEquals(line, timestamp, sentence.getTimestamp());
        assertEquals(line, checksum, sentence.getChecksum());
        assertEquals(line, line, sentence.getOriginalSentence());
        
        //check sentence fields
        assertNotNull(line, sentence.getFields());
        int index = 0;
        for (String field : fields) {
            assertEquals(line, field, sentence.getFields().get(index));
            index++;
        }
    }
    
}
