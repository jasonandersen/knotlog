package com.svhelloworld.knotlog.domain.messages;

import com.svhelloworld.knotlog.measure.Speed;

/**
 * A {@link VesselMessage} that represents a {@link Speed}.
 */
public interface SpeedMessage extends VesselMessage, Speed {
    //noop - a marker interface
}
