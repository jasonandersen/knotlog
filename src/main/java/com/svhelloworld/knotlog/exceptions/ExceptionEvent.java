package com.svhelloworld.knotlog.exceptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An event capturing an uncaught exception.
 */
public class ExceptionEvent {

    private final Throwable exception;

    private final Map<String, Object> context;

    /**
     * Constructor
     * @param exception
     */
    public ExceptionEvent(Throwable exception) {
        this(exception, null);
    }

    /**
     * Constructor
     * @param exception
     * @param context
     */
    public ExceptionEvent(Throwable exception, Map<String, Object> context) {
        this.exception = exception;
        if (context == null) {
            this.context = Collections.unmodifiableMap(new HashMap<>());
        } else {
            this.context = Collections.unmodifiableMap(context);
        }

    }

    /**
     * @return the original exception
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * @return an unmodifiable map containing additional context about this exception - 
     *      will never return null but can be empty
     */
    public Map<String, Object> getContext() {
        return context;
    }

}
