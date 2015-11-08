package com.svhelloworld.knotlog.event;

import com.svhelloworld.knotlog.messages.UnrecognizedMessage;

/**
 * During parsing, an unrecognized message was discovered.
 */
public class UnrecognizedMessageDiscovered {

    private final UnrecognizedMessage message;

    /**
     * Constructor
     * @param message
     * @throws IllegalArgumentException when message is null
     */
    public UnrecognizedMessageDiscovered(UnrecognizedMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        this.message = message;
    }

    /**
     * @return the unrecognized message for this event, will never return null
     */
    public UnrecognizedMessage getUnrecognizedMessage() {
        return message;
    }
}
