package com.svhelloworld.knotlog.measure;

import com.svhelloworld.knotlog.KnotlogException;

/**
 * An exception while converting measurement units.
 * 
 * @author Jason Andersen
 * @since Feb 15, 2010
 *
 */
@SuppressWarnings("serial")
public class ConversionException extends KnotlogException {

    /**
     * Constructor
     */
    public ConversionException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param message 
     * @param cause 
     */
    public ConversionException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param message 
     */
    public ConversionException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param cause 
     */
    public ConversionException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
