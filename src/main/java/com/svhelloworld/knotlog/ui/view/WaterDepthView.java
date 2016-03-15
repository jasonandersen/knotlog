package com.svhelloworld.knotlog.ui.view;

import com.svhelloworld.knotlog.domain.messages.WaterDepth;
import com.svhelloworld.knotlog.i18n.BabelFish;

/**
 * View wrapper for {@link WaterDepth} class.
 */
public class WaterDepthView extends BaseVesselMessageView<WaterDepth> {

    /**
     * @param vesselMessage
     */
    public WaterDepthView(WaterDepth vesselMessage) {
        super(vesselMessage);
    }

    /**
     * @see com.svhelloworld.knotlog.ui.view.VesselMessageView#getValue()
     */
    @Override
    public String getValue() {
        return String.format("%.1f %s", getVesselMessage().getDistance(),
                BabelFish.localize(getVesselMessage().getDistanceUnit()));
    }

    /**
     * @see com.svhelloworld.knotlog.ui.view.VesselMessageView#getSource()
     */
    @Override
    public String getSource() {
        return getVesselMessage().getSource().toString();
    }

}