package com.svhelloworld.knotlog.engine.sources;

import com.svhelloworld.knotlog.KnotlogException;

/**
 * Thrown from a failure while attempting to close a source.
 * 
 * @author Jason Andersen
 * @since Feb 23, 2010
 *
 */
@SuppressWarnings("serial")
public class SourceClosingException extends KnotlogException {

    /**
     * Constructor.
     */
    public SourceClosingException() {
        super();
    }

    /**
     * Constructor.
     * @param arg0
     */
    public SourceClosingException(String arg0) {
        super(arg0);
    }

    /**
     * Constructor.
     * @param arg0
     */
    public SourceClosingException(Throwable arg0) {
        super(arg0);
    }

    /**
     * Constructor.
     * @param arg0
     * @param arg1
     */
    public SourceClosingException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
