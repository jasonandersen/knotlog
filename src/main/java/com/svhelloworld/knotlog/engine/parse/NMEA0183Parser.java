package com.svhelloworld.knotlog.engine.parse;

import java.time.Instant;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.util.Now;

/**
 * Parses single NMEA0183 sentences.
 */
@Service
public class NMEA0183Parser {

    private static final Logger log = LoggerFactory.getLogger(NMEA0183Parser.class);

    /**
     * Message source
     */
    private static final VesselMessageSource SOURCE = VesselMessageSource.NMEA0183;

    /**
     * Event bus to subscribe to events that need parsing
     */
    @Autowired
    private EventBus eventBus;

    /**
     * Message dictionary defining what messages are associated 
     * with individual sentences.
     */
    @Autowired
    private NMEA0183MessageDictionary dictionary;

    /**
     * Interprets a single NMEA0183 sentence into vessel messages. Any @{link VesselMessage}s or
     * {@link UnrecognizedMessage}s discovered during parsing will be posted back to the event bus.
     * @param sentence NMEA0183 sentence
     */
    @Subscribe
    public void parse(final NMEA0183Sentence sentence) {
        log.debug("parsing sentence;text={}", sentence.getOriginalSentence());
        String tag = sentence.getTag();
        List<InstrumentMessageDefinition> definitions = dictionary.getDefinitions(tag);
        if (!sentence.isValid()) {
            handleInvalidSentence(sentence);
        } else if (definitions.isEmpty()) {
            handleUndefinedSentence(sentence);
        } else {
            handleDefinedSentence(sentence, definitions);
        }
    }

    /**
     * Handles NMEA0183 sentences that have definitions.
     * @param sentence
     * @param definitions
     */
    private void handleDefinedSentence(NMEA0183Sentence sentence, List<InstrumentMessageDefinition> definitions) {
        for (InstrumentMessageDefinition definition : definitions) {
            Instant timestamp = getSentenceTimestamp(sentence);
            VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(SOURCE, timestamp, definition,
                    sentence.getFields());
            eventBus.post(message);
        }
    }

    /**
     * @param sentence
     * @return an {@link UnrecognizedMessage} indicating the sentence had a tag that has not been defined
     */
    private void handleUndefinedSentence(NMEA0183Sentence sentence) {
        /*
         * sentence tag has not been defined so create an unrecognized message
         * event
         */
        Instant timestamp = getSentenceTimestamp(sentence);
        MessageFailure failureMode = MessageFailure.UNRECOGNIZED_SENTENCE;
        String identifier = sentence.getTalkerId() + sentence.getTag();
        UnrecognizedMessage message = InstrumentMessageFactory.createUnrecognizedMessage(
                SOURCE, timestamp, failureMode, identifier, sentence.getFields(),
                sentence.getOriginalSentence());
        eventBus.post(message);
    }

    /**
     * @param sentence
     * @return an {@link UnrecognizedMessage} indicating the sentence was invalid
     */
    private void handleInvalidSentence(NMEA0183Sentence sentence) {
        Instant timestamp = getSentenceTimestamp(sentence);
        MessageFailure failureMode = MessageFailure.MALFORMED_SENTENCE;
        List<String> fields = sentence.getFields();
        UnrecognizedMessage message = InstrumentMessageFactory.createUnrecognizedMessage(
                VesselMessageSource.NMEA0183, timestamp, failureMode, fields, sentence.getOriginalSentence());
        eventBus.post(message);
    }

    /**
     * Determines the timestamp to use for the sentence.
     * @param sentence
     * @return the timestamp from the sentence, if available - otherwise will return the current date/time
     */
    private Instant getSentenceTimestamp(NMEA0183Sentence sentence) {
        return sentence.getTimestamp() >= 0 ? Instant.ofEpochMilli(sentence.getTimestamp()) : Now.getInstant();
    }

    /**
     * Allows spring to autowire the event bus.
     * @param eventBus
     */
    @PostConstruct
    private void registerOnEventBus() {
        eventBus.register(this);
    }

}
