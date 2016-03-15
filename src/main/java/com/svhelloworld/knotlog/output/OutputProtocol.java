package com.svhelloworld.knotlog.output;

import com.svhelloworld.knotlog.domain.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessage;

/**
 * Defines a protocol to format message objects for text output.
 * All methods can return either a null value or an empty
 * string if the data type is not supported for a particular
 * protocol.
 * 
 * @author Jason Andersen
 * @since Mar 16, 2010
 *
 */
public interface OutputProtocol {

    /*
     * TODO figure out how we're going to deal with tracks and waypoint sets
     */

    /**
     * @return formatted string on message stream open
     */
    public String streamOpen();

    /**
     * @return formatted string on message stream close
     */
    public String streamClose();

    /**
     * @param message
     * @return formatted string to display an unrecognized message
     */
    public String unrecognizedMessage(UnrecognizedMessage message);

    /**
     * @param message
     * @return
     */
    public String vesselMessage(VesselMessage message);
}
