package com.svhelloworld.knotlog.engine.sources;

import java.io.InputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

/**
 * A streaming source from a serial port. We are using the RXTX API
 * to communicate with the port.
 * 
 * @author Jason Andersen
 * @since Apr 25, 2010
 *
 */
public class SerialPortSource implements RealTimeSource, StreamedSource {

    private static final int PORT_TIMEOUT = 2000;

    /*
     * can we have an algorithm that checks the output stream
     * if it stops, we should attempt to re-open the port
     */

    /**
     * serial port configuration
     */
    private final PortConfig config;
    /**
     * serial port
     */
    private SerialPort serialPort;

    /**
     * Constructor
     * @param config
     */
    public SerialPortSource(PortConfig config) {
        this.config = config;
    }

    /**
     * @see com.svhelloworld.knotlog.engine.sources.StreamedSource#close()
     */
    @Override
    public void close() {
        serialPort.close();
    }

    /**
     * @see com.svhelloworld.knotlog.engine.sources.StreamedSource#open()
     */
    @Override
    public InputStream open() {
        try {
            //make sure the specified port is available
            CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier(config.getName());
            if (identifier.isCurrentlyOwned()) {
                throw new SerialPortOccupiedException(
                        config.getName() + " port is currently opened by another application");
            }
            //open up the port
            CommPort port = identifier.open(getOwnerName(), PORT_TIMEOUT);
            if (port instanceof SerialPort) {

                //configure serial port
                serialPort = (SerialPort) port;
                serialPort.setSerialPortParams(config.getBaudRate(),
                        config.getDataBits().getRXTXConstant(), config.getStopBit().getRXTXConstant(),
                        config.getParity().getRXTXConstant());
                /*
                 * the following 2 lines fix the exception:
                 *       "IOException: Underlying input stream returned zero bytes"
                 *       that is thrown when we read from the InputStream.
                 */
                serialPort.enableReceiveTimeout(PORT_TIMEOUT);
                serialPort.enableReceiveThreshold(0);

                InputStream is = serialPort.getInputStream();
                return is;
            }
            //not a serial port
            throw new SerialPortException("must operate against a serial port");
        } catch (PortInUseException e) {
            throw new SerialPortOccupiedException(e.getMessage(), e);
        } catch (Exception e) {
            throw new SerialPortException(e.getMessage(), e);
        }
    }

    /**
     * @return the name to use as owner of the serial port
     */
    private String getOwnerName() {
        return this.getClass().getName();
    }
}
