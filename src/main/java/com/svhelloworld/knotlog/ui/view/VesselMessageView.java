package com.svhelloworld.knotlog.ui.view;

import com.svhelloworld.knotlog.domain.messages.VesselMessage;

/**
 * Defines a view wrapper around a {@link VesselMessage} to display the message.
 */
public interface VesselMessageView<V extends VesselMessage> {

    public V getVesselMessage();

    public String getLabel();

    public String getValue();

    public String getSource();
}
