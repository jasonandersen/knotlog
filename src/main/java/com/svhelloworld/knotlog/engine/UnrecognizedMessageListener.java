package com.svhelloworld.knotlog.engine;

import com.svhelloworld.knotlog.messages.UnrecognizedMessage;

/**
 * Receives events as unrecognized messages are discovered.
 * 
 * @author Jason Andersen
 * @since Mar 11, 2010
 *
 */
public interface UnrecognizedMessageListener extends MessageDiscoveryListener {
    
    /**
     * @param messages unrecognized messages discovered during the
     * message discovery process
     */
    public void unrecognizedMessagesFound(UnrecognizedMessage... messages);
}
