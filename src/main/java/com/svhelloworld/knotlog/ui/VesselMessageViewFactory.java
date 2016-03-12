package com.svhelloworld.knotlog.ui;

import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.ui.currentstate.VesselMessageView;
import com.svhelloworld.knotlog.ui.currentstate.WaterDepthView;

/**
 * Creates {@link VesselMessageView} objects from {@link VesselMessage} objects.
 * @deprecated
 * @see com.svhelloworld.knotlog.ui.currentstate.VesselMessageViewConverter
 */
@Deprecated
public class VesselMessageViewFactory {

    /**
     * Builds a vessel message view from a vessel message.
     * @param message
     * @return returns a view object for the message types which have associated views, for all
     *      other message types with no associated views, will return null.
     */
    @SuppressWarnings("unchecked")
    public <V extends VesselMessage, T extends VesselMessageView<V>> T buildView(V message) {
        if (message instanceof WaterDepth) {
            WaterDepthView view = new WaterDepthView((WaterDepth) message);
            return (T) view;
        }

        return null;
    }

}
