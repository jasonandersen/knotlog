package com.svhelloworld.knotlog.engine;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.svhelloworld.knotlog.engine.parse.Parser;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * This engine will setup the parser, hook the parser to the
 * preparse listener and vessel message listener objects and 
 * execute the parsing.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
public class ParsingLogEngine implements Runnable {
    /**
     * Source to parse vessel messages from.
     */
    private final StreamedSource source;
    /**
     * Parser used to parse vessel messages.
     */
    private final Parser parser;
    /**
     * Message outputs.
     */
    private final List<VesselMessageListener> messageListeners;
    /**
     * Raw message input data listeners
     */
    private final List<PreparseListener> preparseListeners;
    /**
     * Thread pool for parser and message listeners
     */
    private final ExecutorService threadPool;
    
    /**
     * Constructor
     * @param source source to be parsed
     * @param parser parser to execute against the source
     * @param messageListeners if size is zero, than preparseListeners size must be greater
     *          than zero
     * @param preparseListeners if size is zero, than messageListeners size must be greater
     *          than zero
     * @param threadPool
     * @throws NullPointerException when any of the parameter values are null
     * @throws IllegalArgumentException when the size of both preparseListeners and
     *          messageListeners are zero.
     */
    public ParsingLogEngine(
            final StreamedSource source, 
            final Parser parser, 
            final List<VesselMessageListener> messageListeners, 
            final List<PreparseListener> preparseListeners,
            final ExecutorService threadPool) {
        
        //make sure all parameters are non null
        if (source == null) {
            throw new NullPointerException("source cannot be null");
        }
        if (parser == null) {
            throw new NullPointerException("parser cannot be null");
        }
        if (messageListeners == null) {
            throw new NullPointerException("message listeners list cannot be null");
        }
        if (preparseListeners == null) {
            throw new NullPointerException("preparse listeners list cannot be null");
        }
        if (threadPool == null) {
            throw new NullPointerException("thread pool cannot be null");
        }
        
        //make sure we have listeners
        if (messageListeners.size() + preparseListeners.size() == 0) {
            throw new IllegalArgumentException("must have at least one listener");
        }
        this.source = source;
        this.parser = parser;
        this.messageListeners = messageListeners;
        this.preparseListeners = preparseListeners;
        this.threadPool = threadPool;
    }
    
    /**
     * Starts the vessel message logging.
     */
    @Override
    public void run() {
        //make sure we're initialized properly
        assert threadPool != null;
        assert source != null;
        assert parser != null;
        assert messageListeners != null;
        assert preparseListeners != null;
        assert messageListeners.size() + preparseListeners.size() > 0;
        
        //set up the parser
        parser.setSource(source);
        for (VesselMessageListener listener: messageListeners) {
            parser.addMessageListener(listener);
        }
        for (PreparseListener listener : preparseListeners) {
            parser.addPreparseListener(listener);
        }
        
        //execute the parser
        threadPool.execute(parser);
        monitorParser();
    }
    
    /**
     * Monitors the state of the parser.
     */
    private void monitorParser() {
        while(!parser.isComplete()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO what do we do when interrupted?
                e.printStackTrace();
            }
        }
    }


}
