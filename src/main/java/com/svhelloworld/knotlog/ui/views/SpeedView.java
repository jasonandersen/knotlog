package com.svhelloworld.knotlog.ui.views;

import com.svhelloworld.knotlog.domain.messages.SpeedMessage;
import com.svhelloworld.knotlog.measure.Speed;

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
     * @see com.svhelloworld.knotlog.ui.views.VesselMessageView#getValue()
     */
    @Override
    public String getValue() {
        return String.format("%.1f %s", getVesselMessage().getSpeed(), getVesselMessage().getSpeedUnit());
    }

}
