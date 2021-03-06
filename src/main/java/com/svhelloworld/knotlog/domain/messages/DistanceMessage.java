package com.svhelloworld.knotlog.domain.messages;

import com.svhelloworld.knotlog.measure.Distance;

/**
 * {@link VesselMessage}s that implement {@link Distance}.
 */
public interface DistanceMessage extends VesselMessage, Distance {
    //NOOP - marker interface
}
