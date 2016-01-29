package com.svhelloworld.knotlog.engine.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * Takes an {@link StreamedSource} and reads sentences out of the stream and posts 
 * them as events on to the event bus.
 */
public class NMEA0183Reader {

    private static Logger log = LoggerFactory.getLogger(NMEA0183Reader.class);

    private final EventBus eventBus;

    private final StreamedSource source;

    /**
     * Constructor
     * @param eventBus
     * @param source
     * @throws IllegalArgumentException when eventBus or source is null
     */
    public NMEA0183Reader(EventBus eventBus, StreamedSource source) {
        Validate.notNull(eventBus);
        Validate.notNull(source);
        this.eventBus = eventBus;
        eventBus.register(this);
        this.source = source;
    }

    /**
     * Starts reading from the NMEA0183 source.
     */
    public void start() {
        log.info("Starting parse");
        BufferedReader reader = openSource();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                NMEA0183Sentence sentence = new NMEA0183Sentence(line);
                eventBus.post(sentence);
            }
        } catch (IOException e) {
            throw new ParseException(e);
        } finally {
            closeSource(reader);
            eventBus.unregister(this);
        }
        log.info("Parse complete");
    }

    /**
     * @return a BufferedReader object from the source.
     * @throws NullPointerException if the underlying source is null.
     */
    private BufferedReader openSource() {
        log.debug("Opening source");
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
            //TODO - should probably unregister the reader from the event bus here
            stream.close();
        } catch (IOException e) {
            //ignore
        }
    }

}
