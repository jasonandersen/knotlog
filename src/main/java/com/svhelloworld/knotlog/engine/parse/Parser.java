package com.svhelloworld.knotlog.engine.parse;

import com.svhelloworld.knotlog.engine.PreparseListener;
import com.svhelloworld.knotlog.engine.UnrecognizedMessageListener;
import com.svhelloworld.knotlog.engine.VesselMessageListener;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * Parses encoded vessel message data and converts into
 * domain objects. Any vessel message objects discovered
 * are thrown as events.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
public interface Parser extends Runnable {
    /**
     * Determines the source object to be parsed.
     * @param source
     */
    public void setSource(StreamedSource source);
    /**
     * Begins parsing the provided source. Must be preceded by
     * a call to setSource().
     */
    public void run();
    /**
     * Adds a vessel message listener.
     * @param listener vessel message listener to be notified of messages
     */
    public void addMessageListener(VesselMessageListener listener);
    /**
     * Removes a vessel message listener.
     * @param listener vessel message listener to remove from listening
     * @return true if listener was removed, otherwise false
     */
    public boolean removeMessageListener(VesselMessageListener listener);
    /**
     * Adds an unrecognized message listener.
     * @param listener unrecognized message listener to be notified of
     * unrecognized messages
     */
    public void addUnrecognizedMessageListener(UnrecognizedMessageListener listener);
    /**
     * Removes an unrecognized message listener
     * @param listener listener to be remove from listening
     * @return true if listener was removed, otherwise false
     */
    public boolean removeUnrecognizedMessageListener(UnrecognizedMessageListener listener);
    /**
     * Adds a preparse listener
     * @param listener preparse listener to be notified of preparse data
     */
    public void addPreparseListener(PreparseListener listener);
    /**
     * Removes a preparse listener
     * @param listener preparse listener to be removed
     * @return true if listener was removed, otherwise false
     */
    public boolean removePreparseListener(PreparseListener listener);
    /**
     * @return true if the parsing process is completed, false if
     * parsing is still ongoing.
     */
    public boolean isComplete();
}
