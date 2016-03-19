package com.svhelloworld.knotlog.ui.views;

import com.svhelloworld.knotlog.domain.messages.PositionFormat;
import com.svhelloworld.knotlog.domain.messages.PositionMessage;

/**
 * View wrapper for {@link PositionMessage}s.
 */
public class PositionView extends BaseVesselMessageView<PositionMessage> {

    /**
     * Constructor
     * @param vesselMessage
     */
    public PositionView(PositionMessage vesselMessage) {
        super(vesselMessage);
    }

    /**
     * @see com.svhelloworld.knotlog.ui.views.VesselMessageView#getValue()
     */
    @Override
    public String getValue() {
        return PositionFormat.DEGREES_MINUTES.format(getVesselMessage());
    }

}
