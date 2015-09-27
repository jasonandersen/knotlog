package com.svhelloworld.knotlog.engine.sources;

import com.svhelloworld.knotlog.KnotlogException;

/**
 * An exception relating to operations against a serial port.
 * 
 * @author Jason Andersen
 * @since Apr 26, 2010
 *
 */
@SuppressWarnings("serial")
public class SerialPortException extends KnotlogException {

    /**
     * Constructor
     */
    public SerialPortException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param message 
     * @param cause 
     */
    public SerialPortException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     * @param message 
     */
    public SerialPortException(String message) {
        super(message);
    }

    /**
     * Constructor
     * @param cause 
     */
    public SerialPortException(Throwable cause) {
        super(cause);
    }
}
