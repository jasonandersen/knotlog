package com.svhelloworld.knotlog.events;

/**
 * Thrown when a {@link Request} is posted to the event bus but not handled
 * by any registered component.
 */
public class RequestIgnoredException extends RuntimeException {

    private final Request<?> request;

    /**
     * Constructor
     * @param request
     */
    public RequestIgnoredException(Request<?> request) {
        this.request = request;
    }

    /**
     * @return the ignored request
     */
    public Request<?> getRequest() {
        return this.request;
    }
}
