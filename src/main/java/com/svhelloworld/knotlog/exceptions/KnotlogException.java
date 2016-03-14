package com.svhelloworld.knotlog.exceptions;

/**
 * Base exception class for all application exceptions.
 * 
 * @author Jason Andersen
 * @since Feb 15, 2010
 *
 */
@SuppressWarnings("serial")
public abstract class KnotlogException extends RuntimeException {

    /**
     * Constructor
     */
    public KnotlogException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param message 
     * @param cause 
     */
    public KnotlogException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param message 
     */
    public KnotlogException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param cause 
     */
    public KnotlogException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
