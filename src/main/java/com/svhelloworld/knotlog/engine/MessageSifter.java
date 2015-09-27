package com.svhelloworld.knotlog.engine;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Defines objects that sift through message traffic to
 * prune down the number of messages being output.
 * 
 * @author Jason Andersen
 * @since Feb 18, 2010
 *
 */
public interface MessageSifter {

    /**
     * Sifts through vessel messages.
     */
    public VesselMessage[] sift(VesselMessage... messages);

}
