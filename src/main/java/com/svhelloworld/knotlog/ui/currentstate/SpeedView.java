package com.svhelloworld.knotlog.ui.currentstate;

import com.svhelloworld.knotlog.measure.Speed;
import com.svhelloworld.knotlog.messages.SpeedMessage;

/**
 * View wrapper around {@link Speed} messages.
 */
public class SpeedView extends BaseVesselMessageView<SpeedMessage> {

    /**
     * Constructor
     * @param vesselMessage
     */
    public SpeedView(SpeedMessage vesselMessage) {
        super(vesselMessage);
    }

    /**
     * @see com.svhelloworld.knotlog.ui.currentstate.VesselMessageView#getValue()
     */
    @Override
    public String getValue() {
        return String.format("%.1f %s", getVesselMessage().getSpeed(), getVesselMessage().getSpeedUnit());
    }

}
