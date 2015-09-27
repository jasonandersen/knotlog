package com.svhelloworld.knotlog.engine;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Monitors the message stream for messages that indicate a
 * UTC date and time. Calculates the delta between timestamps
 * in the message stream and actual UTC time and updates 
 * message timestamps so they more accurately reflect UTC time.
 * 
 * @author Jason Andersen
 * @since Mar 14, 2010
 *
 */
public class TimeStamper implements MessageStreamProcessor, Runnable {
    
    /*
     * TODO finish!
     * - first we need to find a reference timestamp
     * - keep cycling through messages until we get a
     *      DateZulu or TimeOfDayZulu.
     * - we need both a DateZulu and TimeOfDayZulu to calculate delta
     * - store timestamp from TimeOfDayZulu
     * - once we have both a Date and a Time, calculate actual time zulu
     * - subtract TimeOfDayZulu.timestamp from derived timestamp
     * - there's yer delta
     * - go back through all the queued messages and apply the delta
     *      to the message timestamps
     * - now, for every message that comes through, apply the delta to the
     *      message's timestamp
     * - give the message back to the parser
     * - if no Date or Time has been found after n seconds, flush the
     *      message queue
     */
    
    /**
     * Next processor to call.
     */
    private MessageStreamProcessor next;
    /**
     * The delta in milliseconds between timestamps in the message
     * stream and actual UTC time as declared by vessel instruments.
     */
    private Long delta = null;
    /**
     * Messages queued waiting to be updated.
     */
    private Queue<VesselMessage> messageBuffer;
    
    /**
     * Constructor.
     */
    public TimeStamper() {
        messageBuffer = new ConcurrentLinkedQueue<VesselMessage>();
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        //TODO implement
    }
    
    /**
     * @see com.svhelloworld.knotlog.engine.MessageStreamProcessor#processMessages(java.util.List)
     */
    @Override
    public void processMessages(VesselMessage... messages) {
        /*
         * TODO implement
         * Still not sure how.
         */
        next.processMessages(messages);
    }
    
    /**
     * @see com.svhelloworld.knotlog.engine.MessageStreamProcessor#setNextProcessor(com.svhelloworld.knotlog.engine.MessageStreamProcessor)
     */
    @Override
    public void setNextProcessor(MessageStreamProcessor next) {
        this.next = next;
    }
    
    /**
     * Updates timestamps on messages queued.
     */
    private void updateTimestamps() {
        assert next != null;
        long orig = 0;
        long updated = 0;
        VesselMessage message;
        while ((message = messageBuffer.poll()) != null) {
            orig = message.getTimestamp().getTime();
            updated = orig + delta;
            message.setTimestamp(new Date(updated));
            /*
             * right now, we're passing the messages off one by one,
             * it might be more efficient to pass the entire message
             * buffer.
             */
            next.processMessages(message);
        }
    }
    
    /**
     * Determines if the timestamp delta has been found and can be applied.
     * @return true if delta is found
     */
    private boolean isDeltaFound() {
        return delta != null;
    }


}
