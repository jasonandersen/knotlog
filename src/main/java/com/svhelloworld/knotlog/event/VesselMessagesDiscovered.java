package com.svhelloworld.knotlog.event;

import com.svhelloworld.knotlog.messages.VesselMessages;

/**
 * Event thrown when new vessel messages have been discovered.
 */
public class VesselMessagesDiscovered {

    private final VesselMessages messages;

    /**
     * Constructor
     * @param messages
     * @throws IllegalArgumentException when messages are null
     */
    public VesselMessagesDiscovered(VesselMessages messages) {
        if (messages == null) {
            throw new IllegalArgumentException("messages cannot be null");
        }
        this.messages = messages;
    }

    /**
     * @return vessel messages discovered, cannot be null
     */
    public VesselMessages getVesselMessages() {
        return this.messages;
    }
}
