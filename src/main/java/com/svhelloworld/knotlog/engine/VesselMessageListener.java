package com.svhelloworld.knotlog.engine;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Receives events as vessel messages are discovered.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
public interface VesselMessageListener extends MessageDiscoveryListener, Runnable {
    /**
     * Vessel message(s) discovered.
     * @param messages discovered
     * @throws MessageRejectedException when this method is called after
     * the listener has been shutdown
     */
    public void vesselMessagesFound(VesselMessage... messages) throws MessageRejectedException;
}
