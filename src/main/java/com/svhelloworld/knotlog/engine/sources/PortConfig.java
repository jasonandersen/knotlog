package com.svhelloworld.knotlog.engine.sources;

import gnu.io.SerialPort;

/**
 * Contains configuration parameters for a communication port.
 * 
 * @author Jason Andersen
 * @since Apr 25, 2010
 *
 */
public class PortConfig {
    
    /**
     * Constructs a standard configuration for communication via serial
     * port to a GPS unit.
     * @param portName name of port
     * @return the default port configuration for a GPS serial port connection:
     *      4800 baud, 8, 1, none
     */
    public static PortConfig getDefaultGpsConfig(String portName) {
        return new PortConfig(portName, 4800, 8, 1, Parity.NONE);
    }
    
    /**
     * Number of databits
     */
    public enum DataBits {
        FIVE(SerialPort.DATABITS_5),
        SIX(SerialPort.DATABITS_6),
        SEVEN(SerialPort.DATABITS_7),
        EIGHT(SerialPort.DATABITS_8);
        
        /**
         * constant used in RXTX API
         */
        private int rxtxConstant;
        
        /**
         * Constructor
         * @param rxtxConstant constant used in RXTX API
         */
        private DataBits(int rxtxConstant) {
            this.rxtxConstant = rxtxConstant;
        }
        
        /**
         * @return constant used in RXTX API
         */
        public int getRXTXConstant() {
            return rxtxConstant;
        }
    }
    
    /**
     * Designated stop bit
     */
    public enum StopBit {
        ONE(SerialPort.STOPBITS_1),
        TWO(SerialPort.STOPBITS_2),
        ONE_FIVE(SerialPort.STOPBITS_1_5);
        

        /**
         * constant used in RXTX API
         */
        private int rxtxConstant;
        
        /**
         * Constructor
         * @param rxtxConstant constant used in RXTX API
         */
        private StopBit(int rxtxConstant) {
            this.rxtxConstant = rxtxConstant;
        }
        
        /**
         * @return constant used in RXTX API
         */
        public int getRXTXConstant() {
            return rxtxConstant;
        }
    }
    
    /**
     * Parity
     */
    public enum Parity {
        NONE(SerialPort.PARITY_NONE),
        ODD(SerialPort.PARITY_ODD),
        EVEN(SerialPort.PARITY_EVEN),
        MARK(SerialPort.PARITY_MARK),
        SPACE(SerialPort.PARITY_MARK);
        
        /**
         * constant used in RXTX API
         */
        private int rxtxConstant;
        
        /**
         * Constructor
         * @param rxtxConstant constant used in RXTX API
         */
        private Parity(int rxtxConstant) {
            this.rxtxConstant = rxtxConstant;
        }
        
        /**
         * @return constant used in RXTX API
         */
        public int getRXTXConstant() {
            return rxtxConstant;
        }
    }
    
    /**
     * name of serial port
     */
    private final String name;
    /**
     * baud rate
     */
    private final int baudRate;
    /**
     * data bits
     */
    private final DataBits dataBits;
    /**
     * stop bit
     */
    private final StopBit stopBit;
    /**
     * parity
     */
    private final Parity parity;
    
    /**
     * Constructor
     * @param name port name
     * @param baudRate baud rate for comm port, must be positive
     * @param dataBits must be 5, 6, 7 or 8
     * @param stopBit must be 1 or 2
     * @param parity 
     * @throws IllegalArgumentException if parameters are outside the specified
     */
    public PortConfig(
            String name,
            int baudRate, 
            int dataBits, 
            int stopBit, 
            Parity parity) {
        
        //set name
        this.name = name;
        
        //set baud rate
        if (baudRate <= 0) {
            throw new IllegalArgumentException("baud rate must be positive: " + baudRate);
        }
        this.baudRate = baudRate;
        
        //set the data bits
        if (dataBits == 5) {
            this.dataBits = DataBits.FIVE;
        } else if (dataBits == 6) {
            this.dataBits = DataBits.SIX;
        } else if (dataBits == 7) {
            this.dataBits = DataBits.SEVEN;
        } else if (dataBits == 8) {
            this.dataBits = DataBits.EIGHT;
        } else {
            throw new IllegalArgumentException("illegal data bits: " + dataBits);
        }
        
        //set the stop bit
        if (stopBit == 1) {
            this.stopBit = StopBit.ONE;
        } else if (stopBit == 2) {
            this.stopBit = StopBit.TWO;
        } else {
            throw new IllegalArgumentException("illegal stop bit: " + stopBit);
        }
        
        //set the parity
        this.parity = parity;
    }
    
    /**
     * @return name of communication port
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the baud rate
     */
    public int getBaudRate() {
        return baudRate;
    }
    
    /**
     * @return the dataBits
     */
    public DataBits getDataBits() {
        return dataBits;
    }

    /**
     * @return the stopBit
     */
    public StopBit getStopBit() {
        return stopBit;
    }

    /**
     * @return the parity
     */
    public Parity getParity() {
        return parity;
    }
    
}
