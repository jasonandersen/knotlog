package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svhelloworld.knotlog.engine.parse.MessageFailure;
import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * An instrument message that was not recognized.
 * 
 * @author Jason Andersen
 * @since Feb 16, 2010
 *
 */
public class UnrecognizedMessage implements VesselMessage {

    /**
     * Key used to retrieve the display message.
     */
    private final static String DISPLAY_KEY = "display.unrecognized";
    /**
     * Key used to retrieve the name.
     */
    private final static String NAME_KEY = "name.unrecognized";

    /**
     * Source of the instrument message.
     */
    private final VesselMessageSource source;
    /**
     * Timestamp of instrument message event.
     */
    private Instant timestamp;
    /**
     * Mode of message failure.
     */
    private final MessageFailure failureMode;
    /**
     * Fields from instrument sentence.
     */
    private final List<String> sentenceFields;
    /**
     * Additional debug info.
     */
    private final List<Object> debugInfo;
    /**
     * Message class being created.
     */
    @SuppressWarnings("rawtypes")
    private final Class messageClass;

    /**
     * Constructor.
     * @param source source of instrument message
     * @param timestamp timestamp of instrument message
     * @param failureMode cause of message failure
     * @param sentenceFields sentence fields
     * @param debugInfo any additional debugging info
     * @throws NullPointerException if failureMode is null
     */
    @SuppressWarnings("rawtypes")
    public UnrecognizedMessage(
            final VesselMessageSource source,
            final Instant timestamp,
            final MessageFailure failureMode,
            final List<String> sentenceFields,
            Object... debugInfo) {

        if (failureMode == null) {
            throw new NullPointerException("failure mode cannot be null");
        }
        this.source = source;
        this.timestamp = timestamp;
        this.failureMode = failureMode;
        this.sentenceFields = new ArrayList<String>();
        this.sentenceFields.addAll(sentenceFields);
        this.debugInfo = Arrays.asList(debugInfo);

        //if a message class was passed in, pull it and store it seperately
        Class messageClass = null;
        for (Object debug : debugInfo) {
            if (debug instanceof Class) {
                messageClass = (Class) debug;
            }
        }
        this.messageClass = messageClass;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getDisplayMessage()
     */
    @Override
    public String getDisplayMessage() {
        return null;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getSource()
     */
    @Override
    public VesselMessageSource getSource() {
        return source;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getTimestamp()
     */
    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * @return an indicator of the cause of failure
     */
    public MessageFailure getFailureMode() {
        return failureMode;
    }

    /**
     * @return the instrument sentence fields
     */
    public List<String> getSentenceFields() {
        return sentenceFields;
    }

    /**
     * @return additional information to aid in debugging message failure
     */
    public List<Object> getDebugInfo() {
        return debugInfo;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getName()
     */
    @Override
    public String getName() {
        return BabelFish.localizeKey(NAME_KEY);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#setTimestamp(java.util.Date)
     */
    @Override
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeKey()
     */
    @Override
    public String getLocalizeKey() {
        return DISPLAY_KEY;
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        String debug = messageClass != null ? messageClass.getSimpleName() : "";
        debug += sentenceFields.toString();
        return MiscUtil.varargsToList(failureMode, debug);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return BabelFish.localize(this);
    }

    /**
     * Defines the natural sorting order as chronological
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(VesselMessage o2) {
        return o2 == null ? 1 : timestamp.compareTo(o2.getTimestamp());
    }

}
