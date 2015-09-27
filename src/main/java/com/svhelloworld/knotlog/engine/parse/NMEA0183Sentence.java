package com.svhelloworld.knotlog.engine.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * An NMEA0183 sentence.
 * 
 * Example:
 * <pre>
 * 124098409893,$GPRMB,A,0.66,L,003,004,4917.24,N,12309.57,W,001.3,052.5,000.5,V*0B[CRLF]
 * </pre>
 * 
 * This sentence would be interpreted into the following atomic values:
 * <ul>
 * <li>timestamp = 124098409893</li>
 * <li>talker ID = GP</li>
 * <li>tag = RMB</li>
 * <li>checksum = 0B</li>
 * <li>fields(comma seperated) = [A,0.66,L,003,004,4917.24,N,12309.57,W,001.3,052.5,000.5,V]</li>
 * </ul>
 * 
 * The first field in the sentence will be evaluated to see if it can be 
 * coerced into a non-negative <tt>long</tt> datatype and stored as timestamp. 
 * Fields will then be evaluated in order - starting with the second field 
 * only if the first field evaluated as a valid timestamp - until a valid sentence 
 * tag is found. Sentence tags will be in the pattern: <tt>$AABBB</tt> where
 * <tt>$</tt> is optional, <tt>AA</tt> is the talker ID and <tt>BBB</tt> 
 * is the tag. Each field following the sentence tag will be added to the 
 * fields list. If the last field is a valid CRC32 checksum - two digit
 * hexadecimal value - it will be stored as the checksum. Otherwise the 
 * last field will be added to the fields list.
 * 
 */
public class NMEA0183Sentence {
    
    /**
     * Indicates the validity of a NMEA 0183 sentence.
     */
    public enum Validity {
        /**
         * Sentence is of valid NMEA0183 sentence form.
         */
        VALID,
        /**
         * Tag is not properly formed or is missing.
         */
        MALFORMED_TAG,
        /**
         * Checksum is not properly formed or is missing.
         */
        MALFORMED_CHECKSUM,
        /**
         * Checksum is not valid. (Not currently supported)
         */
        INVALID_CHECKSUM
    }
    
    /**
     * Regex pattern to validate timestamp field. Expected pattern
     * is a non-negative Java <tt>long</tt> datatype.
     */
    private static final Pattern timestampPattern = Pattern.compile(
            "\\d{1,}");
    /**
     * Regex pattern to validate sentence tag field. Expected pattern:
     * <tt>$AABBB</tt>
     * Where <tt>$</tt> is optional, <tt>AA</tt> is the talker ID and <tt>BBB</tt> 
     * is the tag.
     */
    private static final Pattern tagPattern = Pattern.compile(
            "\\$?[A-Z]{5}");
    /**
     * Regex pattern to validate checksum field. Expected pattern should be
     * a two-digit hexadecimal number.
     */
    private static final Pattern checksumPattern = Pattern.compile(
            "[0-9A-F]{2}");
    /**
     * A timestamp in milliseconds. If timestamp is not present in this
     * sentence, timestamp will be set to -1.
     */
    private final long timestamp;
    /**
     * The original NMEA sentence.
     */
    private final String originalSentence;

    /**
     * The two character talker ID.
     */
    private final String talkerId;

    /**
     * The three character sentence tag.
     */
    private final String tag;

    /**
     * The two digit (hexadecimal) CRC32 checksum.
     */
    private final String checksum;

    /**
     * Sentence fields.
     */
    private final List<String> fields;

    /**
     * Validity of sentence
     */
    private final Validity validity;
    
    /**
     * Constructs a sentence from a single comma seperated line. If the
     * line is a malformed sentence, this constructor will pull as much
     * information as it can out of the sentence. It will not throw any
     * exceptions for a malformed sentence. All fields will be not null,
     * regardless of sentence validity.
     * @param line NMEA sentence line
     * @throws NullPointerException if <tt>line</tt> is null.
     */
    public NMEA0183Sentence(final String line) {
        if (line == null) {
            throw new NullPointerException("line cannot be null");
        }
        
        //setup
        Validity sentenceValidity = Validity.VALID;
        originalSentence = line;
        fields = new ArrayList<String>();
        /*
         * 3.12.2010 If the -1 isn't passed in, trailing empty string
         * fields won't get created.
         */
        String[] tokens = line.split("(,|\\*)", -1);
        
        int index = 0;
        //check for valid timestamp in the first field
        if (index < tokens.length && tokens[index] != null && 
                timestampPattern.matcher(tokens[index]).matches()) {
            timestamp = Long.parseLong(tokens[index]);
            index++;
        } else {
            timestamp = -1;
        }
        
        //cycle through tokens until a valid tag field is found
        String tagField = null;
        while (index < tokens.length && tagField == null) {
            if (tokens[index] != null && tagPattern.matcher(tokens[index]).matches()) {
                tagField = tokens[index];
            }
            index++;
        }
        if (tagField != null) {
            //valid sentence tag was found
            int position = tagField.startsWith("$") ? 1 : 0;
            talkerId = tagField.substring(position, position + 2);
            tag = tagField.substring(position + 2, position + 5);
            
            //fill out fields
            while (index < tokens.length - 1) {
                fields.add(tokens[index]);
                index++;
            }
            //set checksum
            if (index < tokens.length && tokens[index] != null &&
                    checksumPattern.matcher(tokens[index]).matches()) {
                checksum = tokens[index];
            } else {
                checksum = "";
                //if checksum failed validation, add the last field to the field list
                if (index < tokens.length) {
                    fields.add(tokens[index]);
                }
                sentenceValidity = Validity.MALFORMED_CHECKSUM;
            }
        } else {
            //valid sentence tag was not found
            sentenceValidity = Validity.MALFORMED_TAG;
            talkerId = "";
            tag = "";
            checksum = "";
            fields.addAll(Arrays.asList(tokens));
        }
        validity = sentenceValidity;
    }
    
    /**
     * @return the timestamp for this sentence. If timestamp was
     * not found in the sentence fields, then -1 will be returned.
     */
    public long getTimestamp() {
        return timestamp;
    }
    /**
     * @return the originalSentence
     */
    public String getOriginalSentence() {
        return originalSentence;
    }

    /**
     * @return the talkerId
     */
    public String getTalkerId() {
        return talkerId;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @return the checksum
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * @return the fields
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * @return the validity
     */
    public Validity getValidity() {
        return validity;
    }
    
    /**
     * @return true if sentence is in a valid state, false if
     * sentence is not in a valid state
     */
    public boolean isValid() {
        /*
         * we're going to consider MALFORMED_CHECKSUM a valid
         * state since Garmin diagnostics tend to cut off the
         * checksum anyways.
         */
        return Validity.VALID.equals(validity) || 
                Validity.MALFORMED_CHECKSUM.equals(validity);
    }

}