package com.svhelloworld.knotlog.messages;

/**
 * A valid message generated by a vessel (not an {@link UnrecognizedMessage}).
 */
public interface ValidVesselMessage extends VesselMessage {
    //NOOP - marker interface to denote a valid parsed message
}