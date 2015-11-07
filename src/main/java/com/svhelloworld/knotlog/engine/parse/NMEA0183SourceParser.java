package com.svhelloworld.knotlog.engine.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svhelloworld.knotlog.engine.MessageStreamProcessor;
import com.svhelloworld.knotlog.engine.TimeStamper;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.messages.PreparseMessage;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.messages.VesselMessages;
import com.svhelloworld.knotlog.service.NMEA0183ParseService;
import com.svhelloworld.knotlog.util.Now;

/**
 * Reads from NMEA0183 sources and converts NMEA0183 sentences into {@link VesselMessage}s. Manages
 * parse events and communicates to listeners. Handles multi-threaded parsing.
 */
@Service
public class NMEA0183SourceParser extends BaseThreadedParser {

    private static final Logger log = LoggerFactory.getLogger(NMEA0183SourceParser.class);

    /**
     * Initializes any external message stream processors.
     */
    private static List<MessageStreamProcessor> initExternalProcessors() {
        List<MessageStreamProcessor> out = new ArrayList<MessageStreamProcessor>();
        //TODO - change this to be injected from Spring
        /*
         * add a TimeStamper to update vessel message timestamps to the correct
         * time UTC as time messages come through the message stream.
         */
        out.add(new TimeStamper());
        return out;
    }

    /**
     * Parses individual sentences.
     */
    @Autowired
    private NMEA0183ParseService parseService;

    /**
     * Constructor.
     */
    public NMEA0183SourceParser() {
        super(initExternalProcessors());
        log.info("Starting up");
    }

    /**
     * Parses an NMEA0183 data source.
     */
    @Override
    protected void parse() {
        log.info("Starting parse");
        BufferedReader reader = openSource();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                parseLine(line);
            }
        } catch (IOException e) {
            throw new ParseException(e);
        } finally {
            closeSource(reader);
        }
        log.info("Parse complete");
    }

    /**
     * Processes a single line from an NMEA0183 data feed.
     * @param line a single line
     */
    private void parseLine(final String line) {
        //throw the original line to any preparse listeners
        preparse(line);
        VesselMessages messages = parseService.parseSentence(line);
        if (messages.isEmpty() && messages.getUnrecognizedMessages().isEmpty()) {
            return;
        }
        externalProcessing(messages.toArray(new VesselMessage[0]));
        if (messages.containsUnrecognizedMessage() && hasUnrecognizedMessageListeners()) {
            for (UnrecognizedMessage message : messages.getUnrecognizedMessages()) {
                throwUnrecognizedMessageEvent(message);
            }
        }
    }

    /**
     * if there are any preparse listeners, throw a preparse event to them
     * @param line
     */
    private void preparse(String line) {
        if (!hasPreparseListeners()) {
            return;
        }
        PreparseMessage message = new PreparseMessage(VesselMessageSource.NMEA0183, Now.getInstant(), line);
        throwPreparseEvent(message);
    }

    /**
     * @return a BufferedReader object from the source.
     * @throws NullPointerException if the underlying source is null.
     */
    private BufferedReader openSource() {
        log.debug("Opening source");
        StreamedSource source = getSource();
        if (source == null) {
            throw new NullPointerException("source cannot be null");
        }
        InputStream stream = source.open();
        InputStreamReader isr = new InputStreamReader(stream);
        return new BufferedReader(isr);
    }

    /**
     * Close source stream
     * @param stream
     */
    private void closeSource(BufferedReader stream) {
        log.debug("Closing source");
        try {
            stream.close();
        } catch (IOException e) {
            //ignore
        }
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.BaseThreadedParser#externalProcessingComplete(com.svhelloworld.knotlog.messages.VesselMessage[])
     */
    @Override
    protected void externalProcessingComplete(VesselMessage... messages) {
        throwMessageEvent(messages);
    }
}
