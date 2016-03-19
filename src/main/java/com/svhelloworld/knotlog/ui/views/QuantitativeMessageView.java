package com.svhelloworld.knotlog.ui.views;

import com.svhelloworld.knotlog.domain.messages.QuantitativeMessage;

/**
 * A view wrapper around {@link QuantitativeMessage}s.
 */
public class QuantitativeMessageView extends BaseVesselMessageView<QuantitativeMessage> {

    /**
     * @param vesselMessage
     */
    public QuantitativeMessageView(QuantitativeMessage vesselMessage) {
        super(vesselMessage);
    }

    /**
     * @see com.svhelloworld.knotlog.ui.views.VesselMessageView#getValue()
     */
    @Override
    public String getValue() {
        return String.format("%.1f %s", getVesselMessage().getQuantity(), getVesselMessage().getMeasurementUnit().toString());
    }

}
