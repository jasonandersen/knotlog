package com.svhelloworld.knotlog.engine.parse;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.messages.VesselMessages;
import com.svhelloworld.knotlog.service.NMEA0183ParseService;
import com.svhelloworld.knotlog.util.Now;

/**
 * Parses single NMEA0183 sentences.
 */
@Service
public class NMEA0183SentenceParser implements NMEA0183ParseService {

    private static final Logger log = LoggerFactory.getLogger(NMEA0183SentenceParser.class);

    /**
     * Message source
     */
    private static final VesselMessageSource SOURCE = VesselMessageSource.NMEA0183;

    /**
     * Message dictionary defining what messages are associated 
     * with individual sentences.
     */
    @Autowired
    private NMEA0183MessageDictionary dictionary;

    /**
     * Constructor
     */
    public NMEA0183SentenceParser() {
        log.info("Initializing");
    }

    /**
     * Parses an NMEA0183 sentence in string format
     * @param line
     * @return a list of {@link VesselMessage}s.
     */
    @Override
    public VesselMessages parseSentence(String line) {
        log.debug(String.format("parse: %s", line));
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
        log.debug("interpretting sentence: {}", sentence.getValidity());
        VesselMessages messages = new VesselMessages();
        String tag = sentence.getTag();
        List<InstrumentMessageDefinition> definitions = dictionary.getDefinitions(tag);
        if (!sentence.isValid()) {
            messages.add(buildInvalidSentenceMessage(sentence));
        } else if (definitions.isEmpty()) {
            messages.add(buildUndefinedSentenceMessage(sentence));
        } else {
            for (InstrumentMessageDefinition definition : definitions) {
                messages.add(buildDefinedVesselMessage(definition, sentence));
            }
        }
        logMessages(messages);
        return messages;
    }

    /**
     * Logs the returned messages at a DEBUG level
     * @param messages
     */
    private void logMessages(VesselMessages messages) {
        if (!log.isDebugEnabled()) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        if (!messages.isEmpty()) {
            builder.append(messages.size()).append(" messages");
            for (VesselMessage message : messages) {
                builder.append(message.getClass().getSimpleName()).append(" ");
            }
        }
        if (!messages.getUnrecognizedMessages().isEmpty()) {
            builder.append(messages.getUnrecognizedMessages().size()).append(" unrecognized messages ");
            for (UnrecognizedMessage message : messages.getUnrecognizedMessages()) {
                builder.append(message.getFailureMode()).append(" ");
            }
        }
        log.debug(builder.toString());
    }

    /**
     * @param definition
     * @param sentence
     * @return a properly defined {@link VesselMessage} based on the sentence and tag definition
     */
    private VesselMessage buildDefinedVesselMessage(InstrumentMessageDefinition definition, NMEA0183Sentence sentence) {
        Instant timestamp = getSentenceTimestamp(sentence);
        return InstrumentMessageFactory.createInstrumentMessage(SOURCE, timestamp, definition, sentence.getFields());
    }

    /**
     * @param sentence
     * @return an {@link UnrecognizedMessage} indicating the sentence had a tag that has not been defined
     */
    private VesselMessage buildUndefinedSentenceMessage(NMEA0183Sentence sentence) {
        /*
         * sentence tag has not been defined so create an unrecognized message
         * event and return an empty message list
         */
        Instant timestamp = getSentenceTimestamp(sentence);
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
    private VesselMessage buildInvalidSentenceMessage(NMEA0183Sentence sentence) {
        Instant timestamp = getSentenceTimestamp(sentence);
        MessageFailure failureMode = MessageFailure.MALFORMED_SENTENCE;
        List<String> fields = sentence.getFields();
        UnrecognizedMessage message = InstrumentMessageFactory.createUnrecognizedMessage(
                VesselMessageSource.NMEA0183, timestamp, failureMode, fields, sentence.getOriginalSentence());
        return message;
    }

    /**
     * Determines the timestamp to use for the sentence.
     * @param sentence
     * @return the timestamp from the sentence, if available - otherwise will return the current date/time
     */
    private Instant getSentenceTimestamp(NMEA0183Sentence sentence) {
        return sentence.getTimestamp() >= 0 ? Instant.ofEpochMilli(sentence.getTimestamp()) : Now.getInstant();
    }

}
