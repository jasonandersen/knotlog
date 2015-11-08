package com.svhelloworld.knotlog.engine.sources;

import java.io.InputStream;

/**
 * Marker class to indicate that a {@link StreamedSource} is a source of NMEA0183 data.
 */
public class NMEA0183StreamedSource implements StreamedSource {

    private final StreamedSource source;

    /**
     * Constructor
     * @param source
     */
    public NMEA0183StreamedSource(StreamedSource source) {
        if (source == null) {
            throw new IllegalArgumentException("source cannot be null");
        }
        this.source = source;
    }

    /**
     * @see com.svhelloworld.knotlog.engine.sources.StreamedSource#open()
     */
    @Override
    public InputStream open() {
        return source.open();
    }

    /**
     * @see com.svhelloworld.knotlog.engine.sources.StreamedSource#close()
     */
    @Override
    public void close() {
        source.close();
    }

}
