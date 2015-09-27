package com.svhelloworld.knotlog.engine.sources;

/**
 * Indicates that an attempt to open a serial port has failed because
 * another application owns the serial port.
 * 
 * @author Jason Andersen
 * @since Apr 26, 2010
 *
 */
@SuppressWarnings("serial")
public class SerialPortOccupiedException extends SerialPortException {

    /**
     * Constructor
     */
    public SerialPortOccupiedException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param message 
     * @param cause 
     */
    public SerialPortOccupiedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     * @param message 
     */
    public SerialPortOccupiedException(String message) {
        super(message);
    }

    /**
     * Constructor
     * @param cause 
     */
    public SerialPortOccupiedException(Throwable cause) {
        super(cause);
    }
}
