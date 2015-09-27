package com.svhelloworld.knotlog.engine;

/**
 * Listeners that receive events during the message discovery
 * process.
 * 
 * @author Jason Andersen
 * @since Mar 11, 2010
 *
 */
public interface MessageDiscoveryListener {
    /**
     * Indicates that the message discovery process has started.
     */
    public void messageDiscoveryStart();
    /**
     * Indicates that the message discovery process has completed.
     */
    public void messageDiscoveryComplete();
}
