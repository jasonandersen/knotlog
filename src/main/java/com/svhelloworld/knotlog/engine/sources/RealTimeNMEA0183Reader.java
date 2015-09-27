package com.svhelloworld.knotlog.engine.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * <h3>**** EXPERIMENTAL - NOT CURRENTLY USED ***</h3>
 * This class prepends each NMEA0183 sentence with a <tt>long</tt> value
 * representing the current time in milliseconds. This reader should
 * only be used to wrap around real-time NMEA0183 data sources.
 * 
 * @author Jason Andersen
 * @since Mar 9, 2010
 *
 */
public class RealTimeNMEA0183Reader extends BufferedReader {
    
    /**
     * Constructor.
     * @param reader
     */
    public RealTimeNMEA0183Reader(Reader reader) {
        super(reader);
    }
    
    /**
     * Prepend the current time in milliseconds.
     * @see java.io.BufferedReader#readLine()
     */
    @Override
    public String readLine() throws IOException {
        String out = super.readLine();
        long time = System.currentTimeMillis();
        return time + "," + out;
    }

}
