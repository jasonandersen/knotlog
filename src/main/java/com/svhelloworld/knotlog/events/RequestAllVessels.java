package com.svhelloworld.knotlog.events;

import java.util.List;

import com.svhelloworld.knotlog.domain.Vessel;

/**
 * Requests a list of all {@link Vessel}s.
 */
public class RequestAllVessels implements Request<List<Vessel>> {

    private List<Vessel> response;

    /**
     * @see com.svhelloworld.knotlog.events.Request#hasResponse()
     */
    @Override
    public boolean hasResponse() {
        return response != null;
    }

    /**
     * @see com.svhelloworld.knotlog.events.Request#getResponse()
     */
    @Override
    public List<Vessel> getResponse() {
        return response;
    }

    /**
     * @param response
     */
    public void setResponse(List<Vessel> response) {
        this.response = response;
    }

}
