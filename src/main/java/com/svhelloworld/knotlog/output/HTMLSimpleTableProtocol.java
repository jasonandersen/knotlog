package com.svhelloworld.knotlog.output;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Outputs vessel messages into an HTML page.
 * 
 * @author Jason Andersen
 * @since Mar 20, 2010
 *
 */
public class HTMLSimpleTableProtocol implements OutputProtocol {

    private static final String OPEN = "<html><head></head><body><table>\n";

    private static final String CLOSE = "</table></body></html>\n";

    private static final String MSG = "<tr><td>%t</td><td>%s</td></tr>\n";

    /**
     * @see com.svhelloworld.knotlog.output.OutputProtocol#streamClose()
     */
    @Override
    public String streamClose() {
        return CLOSE;
    }

    /**
     * @see com.svhelloworld.knotlog.output.OutputProtocol#streamOpen()
     */
    @Override
    public String streamOpen() {
        return OPEN;
    }

    /**
     * @see com.svhelloworld.knotlog.output.OutputProtocol#unrecognizedMessage(com.svhelloworld.knotlog.messages.UnrecognizedMessage)
     */
    @Override
    public String unrecognizedMessage(UnrecognizedMessage message) {
        String localized = BabelFish.localize(message);
        //TODO run localized through HTML encoding
        return String.format(MSG, message.getTimestamp(), localized);
    }

    /**
     * @see com.svhelloworld.knotlog.output.OutputProtocol#vesselMessage(com.svhelloworld.knotlog.messages.VesselMessage)
     */
    @Override
    public String vesselMessage(VesselMessage message) {
        String localized = BabelFish.localize(message);
        //TODO run localized through HTML encoding
        return String.format(MSG, message.getTimestamp(), localized);
    }

}
