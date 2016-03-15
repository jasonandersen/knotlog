package com.svhelloworld.knotlog.output;

import com.svhelloworld.knotlog.domain.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessage;
import com.svhelloworld.knotlog.i18n.BabelFish;

/**
 * Outputs vessel messages in a localized plain text.
 * 
 * @author Jason Andersen
 * @since Mar 16, 2010
 *
 */
public class PlainTextProtocol implements OutputProtocol {

    /**
     * @see com.svhelloworld.knotlog.output.OutputProtocol#streamClose()
     */
    @Override
    public String streamClose() {
        return null;
    }

    /**
     * @see com.svhelloworld.knotlog.output.OutputProtocol#streamOpen()
     */
    @Override
    public String streamOpen() {
        return null;
    }

    /**
     * @see com.svhelloworld.knotlog.output.OutputProtocol#unrecognizedMessage(com.svhelloworld.knotlog.domain.messages.UnrecognizedMessage)
     */
    @Override
    public String unrecognizedMessage(UnrecognizedMessage message) {
        return BabelFish.localize(message) + "\n";
    }

    /**
     * @see com.svhelloworld.knotlog.output.OutputProtocol#vesselMessage(com.svhelloworld.knotlog.domain.messages.VesselMessage)
     */
    @Override
    public String vesselMessage(VesselMessage message) {
        return BabelFish.localize(message) + "\n";
    }

}
