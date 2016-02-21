package com.svhelloworld.knotlog.events;

/**
 * Event to request an NMEA0183 simulation to start.
 */
public class StartNMEA0183SimulationRequest implements Request<Void> {

    /**
     * @see com.svhelloworld.knotlog.events.Request#hasResponse()
     */
    @Override
    public boolean hasResponse() {
        return false;
    }

    /**
     * @see com.svhelloworld.knotlog.events.Request#getResponse()
     */
    @Override
    public Void getResponse() {
        return null;
    }

}
