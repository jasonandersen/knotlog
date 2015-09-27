package com.svhelloworld.knotlog.output;

import com.svhelloworld.knotlog.KnotlogException;

/**
 * Exception during the output process.
 * 
 * @author Jason Andersen
 * @since Mar 16, 2010
 *
 */
@SuppressWarnings("serial")
public class OutputException extends KnotlogException {

    /**
     * Constructor.
     */
    public OutputException() {
        super();
    }

    /**
     * Constructor.
     * @param message
     * @param cause
     */
    public OutputException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param message
     */
    public OutputException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param cause
     */
    public OutputException(Throwable cause) {
        super(cause);
    }

}
