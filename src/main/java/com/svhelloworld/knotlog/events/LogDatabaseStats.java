package com.svhelloworld.knotlog.events;

/**
 * Event requesting database statistics be logged.
 */
public class LogDatabaseStats {

    private final String message;

    /**
     * Constructor
     * @param message
     */
    public LogDatabaseStats(String message) {
        this.message = message;
    }

    /**
     * @return the message associated with this event
     */
    public String getMessage() {
        return message;
    }
}
