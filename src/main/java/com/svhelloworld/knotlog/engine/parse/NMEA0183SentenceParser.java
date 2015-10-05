package com.svhelloworld.knotlog.engine.parse;

import java.util.Date;
import java.util.List;

import com.svhelloworld.knotlog.engine.messages.VesselMessages;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessageSource;

/**
 * Parses a single NMEA0183 sentence.
 */
public class NMEA0183SentenceParser {

    /**
     * Message source
     */
    private static final VesselMessageSource SOURCE = VesselMessageSource.NMEA0183;

    /**
     * Path to CSV message dictionary
     * 
     * FIXME - inject this with Spring
     */
    private static final String DICTIONARY_PATH = "com/svhelloworld/knotlog/engine/parse/NMEA0183MessageDictionary.csv";

    /**
     * Message dictionary defining what messages are associated 
     * with individual sentences.
     */
    private MessageDictionary dictionary;

    /**
     * Constructor
     */
    public NMEA0183SentenceParser() {
        initializeDictionary();
    }

    /**
     * Initializes NMEA 0183 message dictionary 
     */
    private void initializeDictionary() {
        dictionary = new CSVMessageDictionary();
        dictionary.initialize(DICTIONARY_PATH);
    }

    /**
     * Parses an NMEA0183 sentence in string format
     * @param line
     * @return a list of {@link VesselMessage}s.
     */
    public VesselMessages parseSentence(String line) {
        NMEA0183Sentence sentence = new NMEA0183Sentence(line);
        return interpretSentence(sentence);
    }

    /**
     * Interprets a single NMEA0183 sentence into vessel messages.
     * @param sentence NMEA0183 sentence
     * @return a list of vessel messages interpreted from the sentence.
     *          Can return an empty list if no vessel messages can be
     *          interpreted from this sentence. Will never return null.
     *          Any unrecognized messages will be included.
     */
    private VesselMessages interpretSentence(final NMEA0183Sentence sentence) {
        assert sentence != null;
        assert sentence.isValid();
        assert dictionary != null;

        VesselMessages messages = new VesselMessages();

        String tag = sentence.getTag();
        List<String> fields = sentence.getFields();
        List<InstrumentMessageDefinition> definitions = dictionary.getDefinitions(tag);

        if (!sentence.isValid()) {
            //invalid sentence
            messages.add(invalidSentenceMessage(sentence));
        } else if (definitions.isEmpty()) {
            //unrecognized sentence tag
            messages.add(undefinedSentenceMessage(sentence));
        } else {
            //valid sentence and found dictionary definitions
            for (InstrumentMessageDefinition definition : definitions) {
                Date timestamp = sentence.getTimestamp() >= 0 ? new Date(sentence.getTimestamp()) : new Date();
                VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                        SOURCE, timestamp, definition, fields);
                messages.add(message);
            }
        }
        return messages;
    }

    /**
     * @param sentence
     * @return an {@link UnrecognizedMessage} indicating the sentence had a tag that has not been defined
     */
    private VesselMessage undefinedSentenceMessage(NMEA0183Sentence sentence) {
        /*
         * sentence tag has not been defined so create an unrecognized message
         * event and return an empty message list
         */
        Date timestamp = sentence.getTimestamp() > 0 ? new Date(sentence.getTimestamp()) : new Date();
        MessageFailure failureMode = MessageFailure.UNRECOGNIZED_SENTENCE;
        String identifier = sentence.getTalkerId() + sentence.getTag();
        UnrecognizedMessage message = InstrumentMessageFactory.createUnrecognizedMessage(
                SOURCE, timestamp, failureMode, identifier, sentence.getFields(),
                sentence.getOriginalSentence());
        return message;
    }

    /**
     * @param sentence
     * @return an {@link UnrecognizedMessage} indicating the sentence was invalid
     */
    private VesselMessage invalidSentenceMessage(NMEA0183Sentence sentence) {
        Date timestamp = sentence.getTimestamp() > 0 ? new Date(sentence.getTimestamp()) : new Date();
        MessageFailure failureMode = MessageFailure.MALFORMED_SENTENCE;
        List<String> fields = sentence.getFields();
        UnrecognizedMessage message = InstrumentMessageFactory.createUnrecognizedMessage(
                VesselMessageSource.NMEA0183, timestamp, failureMode, fields, sentence.getOriginalSentence());
        return message;
    }

}
