package com.svhelloworld.knotlog.output;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.svhelloworld.knotlog.engine.MessageDiscoveryListener;
import com.svhelloworld.knotlog.engine.MessageRejectedException;
import com.svhelloworld.knotlog.engine.PreparseListener;
import com.svhelloworld.knotlog.engine.UnrecognizedMessageListener;
import com.svhelloworld.knotlog.engine.VesselMessageListener;
import com.svhelloworld.knotlog.messages.PreparseMessage;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Base class for multithreaded output classes. This class will handle
 * all threading details.<p />
 * 
 * Usage: Implement the <tt>processMessages()</tt> method. 
 * The <tt>getMessageBuffer()</tt> will return the queue of messages.
 * Be sure to remove the messages from the queue or a memory leak will
 * occur. Implement the <tt>open()</tt> method to begin the output
 * process. Implement the <tt>close()</tt> method to end the output
 * process.
 * <p />
 * 
 * Example:
 * <pre>
 * public class ConsoleOutput extends ThreadedOutput {
 *     protected void open() {
 *         System.out.println("starting output");
 *     }
 *     protected void close() {
 *         System.out.println("output complete - " & getMessageCount() & " messages";
 *     }
 *     protected void processMessages() {
 *         while ((message = getMessageBuffer().poll()) != null) {
 *             String localized = BabelFish.localize(message);
 *             System.out.println(localized);
 *         }
 *     }
 * }
 * </pre>
 * 
 * @author Jason Andersen
 * @since Feb 22, 2010
 */
public abstract class ThreadedOutput implements MessageDiscoveryListener, 
        VesselMessageListener, PreparseListener, UnrecognizedMessageListener, Runnable {

    /**
     * Indicates the processing state of this listener.
     */
    private enum State {
        /**
         * Object has been instantiated but not called yet.
         */
        INITIALIZED,
        /**
         * No messages are currently being processed.
         */
        WAITING,
        /**
         * This instance has been added to the thread pool for execution.
         */
        QUEUED,
        /**
         * Messages are actively being processed.
         */
        PROCESSING,
        /**
         * Listener is no longer accepting messages for processing.
         */
        SHUTDOWN
    }
    
    /**
     * The processing state of this listener.
     */
    private State state = State.INITIALIZED;
    /**
     * The lock to acquire when changing state of this listener.
     */
    private final ReadWriteLock stateLock;
    /**
     * A count of all messages passed to this listener.
     */
    private Long messageCount;
    /**
     * The lock to acquire when reading or writing the event count.
     */
    private final ReadWriteLock messageCountLock;
    /**
     * Queue to store messages for processing.
     */
    private final Queue<VesselMessage> messageBuffer;
    /**
     * Thread pool to execute tasks.
     */
    private final Executor threadPool;
    
    /**
     * Override this method to process messages. 
     */
    protected abstract void processMessages();
    /**
     * Override this method to open the listening process.
     */
    protected abstract void open();
    /**
     * Override this method to close the listening process.
     */
    protected abstract void close();

    /**
     * Constructor
     * @param threadPool threadPool to execute this listener within
     */
    public ThreadedOutput(Executor threadPool) {
        this.threadPool = threadPool;
        messageCount = 0L;
        messageBuffer = new ConcurrentLinkedQueue<VesselMessage>();
        stateLock = new ReentrantReadWriteLock(true);
        messageCountLock = new ReentrantReadWriteLock(true);
    }
    
    /**
     * Processes any buffered messages.
     * @see java.lang.Runnable#run()
     */
    @Override
    public final void run() {
        /*
         * make sure on the last run through after this instance
         * has been shutdown that we don't alter the state.
         */
        if (!isShutdown()) {
            setState(State.PROCESSING);
        }
        
        /*
         * call the child class to process queued message objects
         */
        processMessages();
        
        if (!isShutdown()) {
            setState(State.WAITING);
        }
    }
    
    /**
     * @see com.svhelloworld.knotlog.engine.MessageDiscoveryListener#messageDiscoveryStart()
     */
    public final void messageDiscoveryStart() {
        /*
         * this method can get called multiple times on a single startup
         * if this instance is listening to more than one type of message
         * event. Make sure we call open only once.
         */
        if (getState().equals(State.INITIALIZED)) {
            setState(State.WAITING);
            open();
        }
    }
    
    /**
     * @see com.svhelloworld.knotlog.engine.MessageDiscoveryListener#messageDiscoveryComplete()
     */
    public final void messageDiscoveryComplete() {
        /*
         * Make sure we only call shutdown() and close() one time.
         */
        if (!isShutdown()) {
            shutdown();
            close();
        }
    }
    
    /**
     * @see com.svhelloworld.knotlog.engine.VesselMessageListener#vesselMessagesFound(com.svhelloworld.knotlog.messages.VesselMessage[])
     */
    @Override
    public final void vesselMessagesFound(VesselMessage... messages)
            throws MessageRejectedException {
        registerMessages(messages);
    }
    
    /**
     * @see com.svhelloworld.knotlog.engine.UnrecognizedMessageListener#unrecognizedMessagesFound(com.svhelloworld.knotlog.messages.UnrecognizedMessage[])
     */
    @Override
    public final void unrecognizedMessagesFound(UnrecognizedMessage... messages) {
        registerMessages(messages);
    }

    /**
     * @see com.svhelloworld.knotlog.engine.PreparseListener#preparsedInput(com.svhelloworld.knotlog.messages.PreparseMessage)
     */
    @Override
    public final void preparsedInput(PreparseMessage message) {
        registerMessages(message);
    }
    
    /**
     * @return the number of messages passed to this listener.
     */
    public long getMessageCount() {
        messageCountLock.readLock().lock();
        long out;
        try {
            out = messageCount;
        } finally {
            messageCountLock.readLock().unlock();
        }
        return out;
    }
    
    /**
     * @return the queue of message objects.
     */
    protected Queue<VesselMessage> getMessageBuffer() {
        return messageBuffer;
    }

    /**
     * Notifies this listener that messages have been thrown.
     * @param messages any messages discovered
     * @throws MessageRejectedException when this method has been called 
     *          after the listener has been shutdown
     */
    private void registerMessages(VesselMessage... messages) {
        /*
         * If this listener is waiting or processing, we add the messages to the
         * queue. If this listener is shutdown, we can't accept any more messages
         * and throw an exception. If this listener is waiting, then we'll add it 
         * to the thread pool for execution.
         */
        if (!isShutdown()) {
            messageBuffer.addAll(Arrays.asList(messages));
            incrementMessageCount(messages.length);
        } else {
            throw new MessageRejectedException(
                    "Cannot add messages to a listener that has been shutdown.");
        }
        /*
         * only fire off task if this listener is in a waiting state
         */
        if (getState().equals(State.WAITING)) {
            setState(State.QUEUED);
            threadPool.execute(this);
        }
    }

    /**
     * Increments the message count.
     * @param count number of messages to increment the total count by
     */
    private void incrementMessageCount(int count) {
        messageCountLock.writeLock().lock();
        try {
            messageCount += count;
        } finally {
            messageCountLock.writeLock().unlock();
        }
    }

    /**
     * @return true if this instance is in a state of being shutdown
     */
    protected boolean isShutdown() {
        return getState().equals(State.SHUTDOWN);
    }
    
    /**
     * Shut down this listener and block until buffers are emptied.
     */
    private void shutdown() {
        setState(State.SHUTDOWN);
        
        //clear out the buffers
        if (isDataBuffered()) {
            threadPool.execute(this);
        }
        
        //block until the buffers clear
        while (isDataBuffered()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }
    
    /**
     * @return true if buffer contains elements.
     */
    private boolean isDataBuffered() {
        return !messageBuffer.isEmpty();
    }
    
    /**
     * @return the processing state of this listener
     */
    private State getState() {
        stateLock.readLock().lock();
        State out;
        try {
            out = this.state;
        } finally {
            stateLock.readLock().unlock();
        }
        return out;
    }
    
    /**
     * Change the processing state of this listener.
     * @param newState new processing state
     * @throws OutputException when state == SHUTDOWN and newState != SHUTDOWN
     */
    private void setState(State newState) {
        stateLock.writeLock().lock();
        try {
            /*
             * don't call getState(), it will try to grab a read lock while we 
             * have the write lock.
             */
            if (state.equals(State.SHUTDOWN) && !newState.equals(State.SHUTDOWN)) {
                throw new OutputException(
                        "once a listener is shutdown, it's state cannot be changed");
            }
            state = newState;
        } finally {
            stateLock.writeLock().unlock();
        }
    }

}
