package com.svhelloworld.knotlog.engine;

import com.svhelloworld.knotlog.KnotlogException;

/**
 * Thrown when a message is rejected from a listener.
 * 
 * @author Jason Andersen
 * @since Feb 22, 2010
 *
 */
@SuppressWarnings("serial")
public class MessageRejectedException extends KnotlogException {
    
    //TODO flesh out MessageRejectedException
    
    /**
     * Constructor
     */
    public MessageRejectedException() {
        super();
    }

    /**
     * Constructor
     * @param message 
     */
    public MessageRejectedException(String message) {
        super(message);
    }

    /**
     * Constructor
     * @param cause 
     */
    public MessageRejectedException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor
     * @param message 
     * @param cause 
     */
    public MessageRejectedException(String message, Throwable cause) {
        super(message, cause);
    }

}
