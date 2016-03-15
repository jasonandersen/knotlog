package com.svhelloworld.knotlog.ui.view;

import org.apache.commons.lang.Validate;

import com.svhelloworld.knotlog.domain.messages.VesselMessage;

/**
 * The base {@link VesselMessageView} class for implementations to build off.
 */
public abstract class BaseVesselMessageView<M extends VesselMessage> implements VesselMessageView<M> {

    private final M message;

    /**
     * Constructor
     * @param vesselMessage
     */
    public BaseVesselMessageView(M vesselMessage) {
        Validate.notNull(vesselMessage);
        this.message = vesselMessage;
    }

    /**
     * @see com.svhelloworld.knotlog.ui.view.VesselMessageView#getVesselMessage()
     */
    @Override
    public M getVesselMessage() {
        return message;
    }

    /**
     * @see com.svhelloworld.knotlog.ui.view.VesselMessageView#getLabel()
     */
    @Override
    public String getLabel() {
        return message.getName();
    }

    /**
     * @see com.svhelloworld.knotlog.ui.view.VesselMessageView#getSource()
     */
    @Override
    public String getSource() {
        return message.getSource().toString();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return message.toString();
    }

}
