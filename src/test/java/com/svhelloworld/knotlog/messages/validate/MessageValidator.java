package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Validates a type of message.
 */
public interface MessageValidator<M extends VesselMessage> {

    /**
     * Asserts that the vessel message has the expected attributes
     * @param message
     * @param expectedAttributes map of key value pairs describing the expected attributes for the message
     * @throws AssertionError when message did not contain the expected attributes
     */
    public void assertValid(M message, Map<String, String> expectedAttributes);
}
