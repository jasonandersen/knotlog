package com.svhelloworld.knotlog.engine.parse;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.event.NMEA0183SentenceDiscovered;
import com.svhelloworld.knotlog.event.UnrecognizedMessageDiscovered;
import com.svhelloworld.knotlog.event.VesselMessagesDiscovered;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.messages.VesselMessages;
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
    private EventBus eventBus;

    /**
     * Message dictionary defining what messages are associated 
     * with individual sentences.
     */
    @Autowired
    private NMEA0183MessageDictionary dictionary;

    /**
     * Constructor
     */
    public NMEA0183Parser() {
        log.info("Initializing");
    }

    /**
     * Handle NMEA0183 sentence discovery events. Will throw a {@link VesselMessagesDiscovered}
     * back on to the event bus.
     * @param event
     */
    @Subscribe
    public void handleNMEA0183SentenceDiscoveredEvent(NMEA0183SentenceDiscovered event) {
        NMEA0183Sentence sentence = event.getSentence();
        interpretSentence(sentence);
    }

    /**
     * Interprets a single NMEA0183 sentence into vessel messages.
     * @param sentence NMEA0183 sentence
     * @return a list of vessel messages interpreted from the sentence.
     *          Can return an empty list if no vessel messages can be
     *          interpreted from this sentence. Will never return null.
     *          Any unrecognized messages will be included.
     */
    private void interpretSentence(final NMEA0183Sentence sentence) {
        log.debug("interpretting sentence: {}", sentence.getValidity());
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
        VesselMessages messages = new VesselMessages();
        for (InstrumentMessageDefinition definition : definitions) {
            VesselMessage message = buildDefinedVesselMessage(definition, sentence);
            if (message instanceof UnrecognizedMessage) {
                eventBus.post(new UnrecognizedMessageDiscovered((UnrecognizedMessage) message));
            } else {
                messages.add(message);
            }
        }
        if (!messages.isEmpty()) {
            eventBus.post(new VesselMessagesDiscovered(messages));
            logMessages(messages);
        }
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
            builder.append(messages.size()).append(" messages:");
            for (VesselMessage message : messages) {
                builder.append(message.getClass().getSimpleName()).append(",");
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
    private void handleUndefinedSentence(NMEA0183Sentence sentence) {
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
        eventBus.post(new UnrecognizedMessageDiscovered(message));
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
        eventBus.post(new UnrecognizedMessageDiscovered(message));
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
    @Autowired
    private void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

}
