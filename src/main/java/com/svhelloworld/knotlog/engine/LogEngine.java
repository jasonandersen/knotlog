package com.svhelloworld.knotlog.engine;

import com.svhelloworld.knotlog.KnotlogException;

/**
 * Defines a message logging engine.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
@Deprecated
public interface LogEngine {
    /**
     * Initializes the logging engine.
     * @param parameters 
     */
    public void initialize(Object... parameters);
    /**
     * Starts the logging engine. Must be preceded by a call to initialize().
     * @throws KnotlogException 
     */
    public void start() throws KnotlogException;
    /**
     * Interrupts the logging engine if it is multithreaded.
     */
    public void stop();
}
