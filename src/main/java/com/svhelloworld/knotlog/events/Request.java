package com.svhelloworld.knotlog.events;

/**
 * Events that represent a request to another component in the
 * application.
 */
public interface Request<R> {

    /**
     * @return true if this request has a response
     */
    public boolean hasResponse();

    /**
     * @return the response to this request
     */
    public R getResponse();
}
