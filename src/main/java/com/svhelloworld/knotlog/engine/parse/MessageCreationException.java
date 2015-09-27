package com.svhelloworld.knotlog.engine.parse;

/**
 * Thrown when a vessel message cannot be created during the 
 * parsing process.
 * 
 * @author Jason Andersen
 * @since Feb 23, 2010
 *
 */
@SuppressWarnings("serial")
public class MessageCreationException extends ParseException {

    /**
     * Constructor.
     */
    public MessageCreationException() {
        super();
    }

    /**
     * Constructor.
     * @param arg0
     * @param arg1
     */
    public MessageCreationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Constructor.
     * @param arg0
     */
    public MessageCreationException(String arg0) {
        super(arg0);
    }

    /**
     * Constructor.
     * @param arg0
     */
    public MessageCreationException(Throwable arg0) {
        super(arg0);
    }

}
