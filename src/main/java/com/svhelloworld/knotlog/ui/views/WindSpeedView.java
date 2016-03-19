package com.svhelloworld.knotlog.ui.views;

import com.svhelloworld.knotlog.domain.messages.WindSpeed;

/**
 * View wrapper for a {@link WindSpeed} message.
 */
public class WindSpeedView extends BaseVesselMessageView<WindSpeed> {

    /**
     * Constructor
     * @param source
     */
    public WindSpeedView(WindSpeed vesselMessage) {
        super(vesselMessage);
    }

    /**
     * @see com.svhelloworld.knotlog.ui.views.VesselMessageView#getValue()
     */
    @Override
    public String getValue() {
        return String.format("%.1f %s", getVesselMessage().getSpeed(), getVesselMessage().getSpeedUnit().toString());
    }

}
