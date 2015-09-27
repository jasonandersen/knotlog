package com.svhelloworld.knotlog.output;

import java.util.Queue;
import java.util.concurrent.Executor;

import com.svhelloworld.knotlog.engine.VesselMessageListener;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.output.ThreadedOutput;

/**
 * A quick and dirty test class to throw messages at the
 * System.out console using the ThreadedMessageOutput class.
 * 
 * @author Jason Andersen
 * @since Feb 22, 2010
 *
 */
public class ConsoleOutputMessageListener extends ThreadedOutput 
        implements VesselMessageListener {
    
    /**
     * buffers messages 
     */
    private final static int BUFFER_SIZE = 10;
    
    /**
     * @param threadPool 
     * 
     */
    public ConsoleOutputMessageListener(Executor threadPool) {
        super(threadPool);
    }

    /**
     * @see com.svhelloworld.knotlog.output.ThreadedOutput#close()
     */
    @Override
    protected void close() {
        //make sure queue is flushed
        if (!getMessageBuffer().isEmpty()) {
            flushQueue();
        }
    }

    /**
     * @see com.svhelloworld.knotlog.output.ThreadedOutput#open()
     */
    @Override
    protected void open() {
        // TODO Auto-generated method stub

    }
    
    /**
     * 
     * @see com.svhelloworld.knotlog.output.ThreadedOutput#processMessages()
     */
    @Override
    protected void processMessages() {
        if (getMessageBuffer().size() > BUFFER_SIZE || isShutdown()) {
            flushQueue();
        }
    }
    
    /**
     * writes queue out to console
     */
    protected void flushQueue() {
        Queue<VesselMessage> queue = getMessageBuffer();
        VesselMessage message = queue.poll();
        while (message != null) {
            System.out.println(message.getDisplayMessage());
            message = queue.poll();
        }
        //System.out.println("queue flushed");
    }

}
