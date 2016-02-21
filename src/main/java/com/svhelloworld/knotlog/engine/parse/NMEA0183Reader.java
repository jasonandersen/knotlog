package com.svhelloworld.knotlog.engine.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * Takes an {@link StreamedSource} and reads sentences out of the stream and posts 
 * them as events on to the event bus.
 */
public class NMEA0183Reader implements Runnable {

    private static Logger log = LoggerFactory.getLogger(NMEA0183Reader.class);

    private final EventBus eventBus;

    private final StreamedSource source;

    private BufferedReader reader;

    private final AtomicBoolean isReading;

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
        this.source = source;
        isReading = new AtomicBoolean(false);
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        start();
    }

    /**
     * Starts reading from the NMEA0183 source.
     */
    public void start() {
        log.info("Starting parse");
        isReading.set(true);
        reader = openSource();
        String line;
        try {
            while ((line = fetchNextLine()) != null) {
                handleLine(line);
            }
        } catch (IOException e) {
            throw new ParseException(e);
        } finally {
            closeSource(reader);
            isReading.set(false);
            eventBus.unregister(this);
        }
        log.info("Parse complete");
    }

    /**
     * @return true if this reader is actively reading
     */
    public boolean isReading() {
        return isReading.get();
    }

    /**
     * Hook to override how this reader reads from the buffered reader.
     * @return return the next line in the reader, will return null if reader is empty
     * @throws IOException 
     */
    protected String fetchNextLine() throws IOException {
        return reader.readLine();
    }

    /**
     * Handles a single line from the source. This method can be overriden by subclasses
     * wanting to change how source text lines are handled.
     * @param line
     */
    protected void handleLine(String line) {
        NMEA0183Sentence sentence = new NMEA0183Sentence(line);
        eventBus.post(sentence);
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
            stream.close();
        } catch (IOException e) {
            //ignore
        }
    }

    /**
     * @return the buffered reader being used to read the source
     */
    protected BufferedReader getReader() {
        return reader;
    }

}
