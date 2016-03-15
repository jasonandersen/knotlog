package com.svhelloworld.knotlog.ui.view;

import com.svhelloworld.knotlog.domain.messages.RudderAngle;

/**
 * View wrapper for a {@link RudderAngle} message.
 */
public class RudderAngleView extends BaseVesselMessageView<RudderAngle> {

    /**
     * @param source
     */
    public RudderAngleView(RudderAngle message) {
        super(message);
    }

    /**
     * @see com.svhelloworld.knotlog.ui.view.VesselMessageView#getValue()
     */
    @Override
    public String getValue() {
        return String.format("%.1f° %s", getVesselMessage().getRudderAngle(), getVesselMessage().getRudderVesselSide());
    }

}
