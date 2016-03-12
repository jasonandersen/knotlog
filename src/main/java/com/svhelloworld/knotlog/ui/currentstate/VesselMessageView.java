package com.svhelloworld.knotlog.ui.currentstate;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Defines a view wrapper around a {@link VesselMessage} to display the message.
 */
public interface VesselMessageView<V extends VesselMessage> {

    public V getVesselMessage();

    public String getLabel();

    public String getValue();

    public String getSource();
}
