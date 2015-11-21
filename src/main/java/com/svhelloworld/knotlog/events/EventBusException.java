package com.svhelloworld.knotlog.events;

/**
 * An exception that occured while posting an event on the event bus.
 */
public class EventBusException extends RuntimeException {

    /**
     * Constructor.
     * @param e
     */
    public EventBusException(Throwable e) {
        super(e);
    }

}
