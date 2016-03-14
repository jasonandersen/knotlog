package com.svhelloworld.knotlog.engine.sources;

import com.svhelloworld.knotlog.exceptions.KnotlogException;

/**
 * Thrown when a source's input stream could not be loaded.
 * 
 * @author Jason Andersen
 * @since Feb 23, 2010
 *
 */
@SuppressWarnings("serial")
public class SourceInputStreamInvalidException extends KnotlogException {

    /**
     * Constructor.
     */
    public SourceInputStreamInvalidException() {
        super();
    }

    /**
     * Constructor.
     * @param arg0
     */
    public SourceInputStreamInvalidException(String arg0) {
        super(arg0);
    }

    /**
     * Constructor.
     * @param arg0
     */
    public SourceInputStreamInvalidException(Throwable arg0) {
        super(arg0);
    }

    /**
     * Constructor.
     * @param arg0
     * @param arg1
     */
    public SourceInputStreamInvalidException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
