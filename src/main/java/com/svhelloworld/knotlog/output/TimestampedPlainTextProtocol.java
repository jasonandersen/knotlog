package com.svhelloworld.knotlog.output;

import com.svhelloworld.knotlog.domain.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessage;
import com.svhelloworld.knotlog.i18n.BabelFish;

/**
 * Each message output is preceded by a current timestamp formatted 
 * in milliseconds since the epoch.
 * 
 * @author Jason Andersen
 * @since May 22, 2010
 *
 */
public class TimestampedPlainTextProtocol implements OutputProtocol {

    /**
     * format mask for preparse output
     */
    private static final String MASK = "%d,%s\n";

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
        return String.format(MASK, message.getTimestamp(),
                BabelFish.localize(message) + "\n");
    }

    /**
     * @see com.svhelloworld.knotlog.output.OutputProtocol#vesselMessage(com.svhelloworld.knotlog.domain.messages.VesselMessage)
     */
    @Override
    public String vesselMessage(VesselMessage message) {
        return String.format(MASK, message.getTimestamp(),
                BabelFish.localize(message) + "\n");
    }

}
