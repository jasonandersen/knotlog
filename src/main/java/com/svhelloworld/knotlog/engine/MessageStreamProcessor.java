package com.svhelloworld.knotlog.engine;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * An object external to a <tt>Parser</tt> that performs work 
 * on messages prior to their distribution. MessageStreamProcessors
 * can be chained together to create a chain of work.<p />
 * 
 * The <tt>processMessages</tt> method does not return messages. 
 * Each processor should complete its work on the messages and 
 * call <tt>processMessages</tt> method on the next processor 
 * in the chain. This allows for asynchronous processing.
 * 
 * @author Jason Andersen
 * @since Mar 14, 2010
 *
 */
public interface MessageStreamProcessor {
    
    /**
     * Processes messages
     * @param messages
     */
    public void processMessages(VesselMessage... messages);
    /**
     * Sets the next message processor.
     * @param next 
     */
    public void setNextProcessor(MessageStreamProcessor next);
}
