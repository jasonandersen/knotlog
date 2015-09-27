package com.svhelloworld.knotlog.engine.sources;

import java.io.InputStream;

/**
 * A source of vessel message data that provides an InputStream.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
public interface StreamedSource {
    /**
     * @return the inputstream from the data source.
     */
    public InputStream open();
    /**
     * Closes out the datasource.
     */
    public void close();
}
