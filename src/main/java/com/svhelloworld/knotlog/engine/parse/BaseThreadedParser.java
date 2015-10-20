package com.svhelloworld.knotlog.engine.parse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import com.svhelloworld.knotlog.engine.MessageDiscoveryListener;
import com.svhelloworld.knotlog.engine.MessageStreamProcessor;
import com.svhelloworld.knotlog.engine.PreparseListener;
import com.svhelloworld.knotlog.engine.UnrecognizedMessageListener;
import com.svhelloworld.knotlog.engine.VesselMessageListener;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.messages.PreparseMessage;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Base class for threaded parsers. This class will handle the
 * multithreading details for child parser classes.
 * 
 * @author Jason Andersen
 * @since Feb 23, 2010
 *
 */
public abstract class BaseThreadedParser implements Runnable, Parser, MessageStreamProcessor {
    /**
     * Indicates the processing state of this parser.
     */
    private enum State {
        /**
         * Parsing has not begun yet.
         */
        INITIALIZED,
        /**
         * Parsing process is going.
         */
        PARSING,
        /**
         * Parsing was interrupted.
         */
        INTERRUPTED,
        /**
         * Parsing is complete.
         */
        COMPLETE
    }

    private static final Logger log = Logger.getLogger(BaseThreadedParser.class);

    /**
     * Indicates the processing state of this parser.
     */
    private State state = State.INITIALIZED;
    /**
     * Lock to acquire for reading and writing the state variable.
     */
    private final ReadWriteLock stateLock;

    /*
     * Note - I'm not currently using read/write locks on the three listener
     * sets. I believe the CopyOnWriteArraySet implementation should be sufficient
     * to handle concurrency for these collections. If it proves to be a 
     * problem, I'll go back in and add locks.
     */
    /**
     * Vessel message listeners.
     */
    private final Set<VesselMessageListener> messageListeners;
    /**
     * Preparse input listeners.
     */
    private final Set<PreparseListener> preparseListeners;
    /**
     * Unrecognized message listeners.
     */
    private final Set<UnrecognizedMessageListener> unrecognizedListeners;
    /**
     * External message stream processing chain.
     */
    private final MessageStreamProcessor externalProcessingChain;
    /**
     * Source object to parse.
     */
    private StreamedSource source;

    /**
     * Contains the parsing code.
     */
    protected abstract void parse();

    /**
     * Method called when external processors complete.
     * @param messages message stream post-processing
     */
    protected abstract void externalProcessingComplete(VesselMessage... messages);

    /**
     * Constructor with no external message processors.
     */
    protected BaseThreadedParser() {
        this(new ArrayList<MessageStreamProcessor>());
    }

    /**
     * Constructor
     * @param externalProcessors any external message processors to act on the
     *          parsing output
     */
    protected BaseThreadedParser(List<MessageStreamProcessor> externalProcessors) {
        messageListeners = new CopyOnWriteArraySet<VesselMessageListener>();
        preparseListeners = new CopyOnWriteArraySet<PreparseListener>();
        unrecognizedListeners = new CopyOnWriteArraySet<UnrecognizedMessageListener>();
        stateLock = new ReentrantReadWriteLock();

        //setup external processor chain
        if (externalProcessors == null || externalProcessors.isEmpty()) {
            externalProcessingChain = null;
        } else {
            /*
             * chain all the message processors together in a one-way linked list.
             * the tail of the linked list should be this parser so it receives
             * a callback with the messages after all the processors are complete.
             */
            MessageStreamProcessor current;
            MessageStreamProcessor next;
            for (int i = 0; i < externalProcessors.size(); i++) {
                current = externalProcessors.get(i);
                next = (i + 1 == externalProcessors.size()) ? this : externalProcessors.get(i + 1);
                current.setNextProcessor(next);
            }
            externalProcessingChain = externalProcessors.get(0);
        }
    }

    /**
     * Runs the parsing process.
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        log.info("Run started");
        setState(State.PARSING);
        throwParsingStartedEvent();
        parse();
        setState(State.COMPLETE);
        throwParsingCompleteEvent();
        log.info("Run complete");
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.Parser#addMessageListener(com.svhelloworld.knotlog.engine.VesselMessageListener)
     */
    @Override
    public void addMessageListener(VesselMessageListener listener) {
        messageListeners.add(listener);
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.Parser#addUnrecognizedMessageListener(com.svhelloworld.knotlog.engine.UnrecognizedMessageListener)
     */
    @Override
    public void addUnrecognizedMessageListener(UnrecognizedMessageListener listener) {
        unrecognizedListeners.add(listener);
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.Parser#addPreparseListener(com.svhelloworld.knotlog.engine.PreparseListener)
     */
    @Override
    public void addPreparseListener(PreparseListener listener) {
        preparseListeners.add(listener);
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.Parser#isComplete()
     */
    @Override
    public boolean isComplete() {
        return getState().equals(State.COMPLETE);
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.Parser#removeMessageListener(com.svhelloworld.knotlog.engine.VesselMessageListener)
     */
    @Override
    public boolean removeMessageListener(VesselMessageListener listener) {
        return messageListeners.remove(listener);
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.Parser#removePreparseListener(com.svhelloworld.knotlog.engine.PreparseListener)
     */
    @Override
    public boolean removePreparseListener(PreparseListener listener) {
        return preparseListeners.remove(listener);
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.Parser#removeUnrecognizedMessageListener(com.svhelloworld.knotlog.engine.UnrecognizedMessageListener)
     */
    @Override
    public boolean removeUnrecognizedMessageListener(UnrecognizedMessageListener listener) {
        return unrecognizedListeners.remove(listener);
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.Parser#setSource(com.svhelloworld.knotlog.engine.sources.StreamedSource)
     */
    @Override
    public void setSource(StreamedSource source) {
        this.source = source;
    }

    /**
     * Callback method for external <tt>MessageStreamProcessor</tt> objects.
     * @see com.svhelloworld.knotlog.engine.MessageStreamProcessor#processMessages(com.svhelloworld.knotlog.messages.VesselMessage[])
     */
    @Override
    public final void processMessages(VesselMessage... messages) {
        externalProcessingComplete(messages);
    }

    /**
     * @see com.svhelloworld.knotlog.engine.MessageStreamProcessor#setNextProcessor(com.svhelloworld.knotlog.engine.MessageStreamProcessor)
     */
    @Override
    public final void setNextProcessor(MessageStreamProcessor next) {
        //this object is the end of the chain, there is no next processor
    }

    /**
     * @return the parsing source.
     */
    protected StreamedSource getSource() {
        return source;
    }

    /**
     * Sends any parsed vessel messages to the external message stream
     * processing chain for further refining.
     * @param messages
     */
    protected void externalProcessing(VesselMessage... messages) {
        if (externalProcessingChain == null) {
            /*
             * no external processing to be done
             */
            externalProcessingComplete(messages);
        } else {
            /*
             * give the external processors a crack at processing the
             * vessel message stream. external processors will use the
             * processMessages() method in this instance as the callback 
             * to pass back the vessel messages.
             */
            externalProcessingChain.processMessages(messages);
        }
    }

    /**
     * Alerts all the listeners that parsing has begun.
     */
    protected void throwParsingStartedEvent() {
        for (MessageDiscoveryListener listener : getAllListeners()) {
            listener.messageDiscoveryStart();
        }
    }

    /**
     * Alerts all the listeners that parsing is complete.
     */
    protected void throwParsingCompleteEvent() {
        for (MessageDiscoveryListener listener : getAllListeners()) {
            listener.messageDiscoveryComplete();
        }
    }

    /**
     * Alerts all the preparse listeners of preparsed data.
     * @param preparsed preparsed text
     */
    protected void throwPreparseEvent(PreparseMessage preparsed) {
        log.debug(String.format("preparse event: %s", preparsed));
        for (PreparseListener listener : preparseListeners) {
            listener.preparsedInput(preparsed);
        }
    }

    /**
     * Alerts all the message listeners of vessel messages parsed.
     * @param messages vessel messages parsed.
     */
    protected void throwMessageEvent(VesselMessage... messages) {
        log.debug(String.format("%d vessel messages found", messages.length));
        for (VesselMessageListener listener : messageListeners) {
            listener.vesselMessagesFound(messages);
        }
    }

    /**
     * Alerts all unrecognized message listeners that unrecognized messages
     * have been found.
     * @param messages unrecognized messages
     */
    protected void throwUnrecognizedMessageEvent(UnrecognizedMessage... messages) {
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder();
            for (UnrecognizedMessage message : messages) {
                builder.append(message.toString()).append(" ");
            }
            log.debug(String.format("unrecognized messages event: %s", builder.toString()));
        }

        for (UnrecognizedMessageListener listener : unrecognizedListeners) {
            listener.unrecognizedMessagesFound(messages);
        }
    }

    /**
     * @return true if this parser has any vessel message listeners
     */
    protected boolean hasVesselMessageListeners() {
        return !messageListeners.isEmpty();
    }

    /**
     * @return true if this parser has unrecognized message listeners
     */
    protected boolean hasUnrecognizedMessageListeners() {
        return !unrecognizedListeners.isEmpty();
    }

    /**
     * @return true if this parser has preparse message listeners
     */
    protected boolean hasPreparseListeners() {
        return !preparseListeners.isEmpty();
    }

    /**
     * @return a set containing all listener objects.
     */
    private Set<MessageDiscoveryListener> getAllListeners() {
        Set<MessageDiscoveryListener> out = new HashSet<MessageDiscoveryListener>();
        out.addAll(messageListeners);
        out.addAll(unrecognizedListeners);
        out.addAll(preparseListeners);
        return out;
    }

    /**
     * @return the processing state of this parser.
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
     * Change the processing state of this parser.
     * @param newState new processing state
     */
    private void setState(State newState) {
        stateLock.writeLock().lock();
        try {
            state = newState;
        } finally {
            stateLock.writeLock().unlock();
        }
    }

}
