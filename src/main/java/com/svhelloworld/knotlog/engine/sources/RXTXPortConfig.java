package com.svhelloworld.knotlog.engine.sources;

import gnu.io.SerialPort;

/**
 * This class acts as an adapter between the <tt>PortConfig</tt>
 * class and the configuration constants used by the RXTX API.
 * 
 * @author Jason Andersen
 * @since Apr 25, 2010
 * @deprecated proved to be a pain in the tookus
 */
@Deprecated
public class RXTXPortConfig {
    
    /**
     * port configuration object to convert to RXTX constants
     */
    private PortConfig config;
    
    /**
     * 
     * Constructor
     * @param config original port configuration
     */
    public RXTXPortConfig(PortConfig config) {
        this.config = config;
    }
    
    /**
     * @return data bits constant
     */
    public int getDataBits() {
        switch (config.getDataBits()) {
        case SEVEN:
            return SerialPort.DATABITS_7;
        case EIGHT:
            return SerialPort.DATABITS_8;
        default:
            throw new IllegalArgumentException("unrecognized data bits: " + config.getDataBits());
        }
    }
    
    /**
     * @return stop bit constant
     */
    public int getStopBit() {
        switch(config.getStopBit()) {
        case ONE:
            return SerialPort.STOPBITS_1;
        default:
            throw new IllegalArgumentException("unrecognized stop bit: " + config.getStopBit());
        }
    }
    
    
}
