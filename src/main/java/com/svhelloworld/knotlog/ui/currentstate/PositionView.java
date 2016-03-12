package com.svhelloworld.knotlog.ui.currentstate;

import com.svhelloworld.knotlog.messages.PositionFormat;
import com.svhelloworld.knotlog.messages.PositionMessage;

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
     * @see com.svhelloworld.knotlog.ui.currentstate.VesselMessageView#getValue()
     */
    @Override
    public String getValue() {
        return PositionFormat.DEGREES_MINUTES.format(getVesselMessage());
    }

}
