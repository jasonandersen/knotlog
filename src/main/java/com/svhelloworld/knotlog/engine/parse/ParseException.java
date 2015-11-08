package com.svhelloworld.knotlog.engine.parse;

import com.svhelloworld.knotlog.KnotlogException;

/**
 * Generic exception thrown during the parsing process.
 * 
 * @author Jason Andersen
 * @since Feb 17, 2010
 *
 */
@SuppressWarnings("serial")
public class ParseException extends KnotlogException {

    /**
     * Constructor
     */
    public ParseException() {
        super();
    }

    /**
     * Constructor
     * @param message 
     * @param cause 
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     * @param message 
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Constructor
     * @param cause 
     */
    public ParseException(Throwable cause) {
        super(cause);
    }
}
