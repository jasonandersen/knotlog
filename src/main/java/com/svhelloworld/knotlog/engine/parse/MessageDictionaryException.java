package com.svhelloworld.knotlog.engine.parse;

/**
 * Thrown when there is a problem loading the 
 * message dictionary.
 * 
 * @author Jason Andersen
 * @since Feb 17, 2010
 *
 */
@SuppressWarnings("serial")
public class MessageDictionaryException extends ParseException {

    /**
     * Constructor
     */
    public MessageDictionaryException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param message 
     * @param cause 
     */
    public MessageDictionaryException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param message 
     */
    public MessageDictionaryException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param cause 
     */
    public MessageDictionaryException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
