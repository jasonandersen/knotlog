package com.svhelloworld.knotlog.engine;

import com.svhelloworld.knotlog.messages.PreparseMessage;

/**
 * Preparse listeners receive vessel message input in
 * its original source pattern prior to parsing. They can
 * be used to capture the original source stream to a file,
 * screen, database, etc.
 * 
 * @author Jason Andersen
 * @since Feb 22, 2010
 *
 */
public interface PreparseListener extends MessageDiscoveryListener, Runnable {
    /**
     * Input data has been received.
     * @param message raw vessel message input data
     */
    public void preparsedInput(PreparseMessage message);
}
