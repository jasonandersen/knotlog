package com.svhelloworld.knotlog.ui.view;

import com.svhelloworld.knotlog.messages.WindSpeed;

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
     * @see com.svhelloworld.knotlog.ui.view.VesselMessageView#getValue()
     */
    @Override
    public String getValue() {
        return String.format("%.1f %s", getVesselMessage().getSpeed(), getVesselMessage().getSpeedUnit().toString());
    }

}
