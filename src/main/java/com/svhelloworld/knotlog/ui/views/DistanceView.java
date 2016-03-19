package com.svhelloworld.knotlog.ui.views;

import com.svhelloworld.knotlog.domain.messages.DistanceMessage;

/**
 * View wrapper around {@link DistanceMessage}s.
 */
public class DistanceView extends BaseVesselMessageView<DistanceMessage> {

    /**
     * Constructor
     * @param vesselMessage
     */
    public DistanceView(DistanceMessage vesselMessage) {
        super(vesselMessage);
    }

    /**
     * @see com.svhelloworld.knotlog.ui.views.VesselMessageView#getValue()
     */
    @Override
    public String getValue() {
        return String.format("%.1f %s", getVesselMessage().getDistance(), getVesselMessage().getDistanceUnit().toString());
    }

}
