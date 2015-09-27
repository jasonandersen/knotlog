package com.svhelloworld.knotlog.output;

import java.util.concurrent.Executor;


/**
 * Outputs messages to the system console. Will default to
 * the <tt>PlainTextProtocol</tt> protocol class unless
 * a protocol is specified in the constructor.
 * 
 * @author Jason Andersen
 * @since Mar 16, 2010
 *
 */
public class ConsoleOutput extends TextOutput {
    
    /**
     * Constructor.
     * @param threadPool thread pool
     * @param protocol protocol to format text in
     */
    public ConsoleOutput(Executor threadPool, OutputProtocol protocol) {
        super(threadPool, System.out, protocol);
    }
    
    /**
     * Constructor. Will default to <tt>PlainTextMessageProtocol</tt> 
     * protocol class
     * @param threadPool thread pool
     */
    public ConsoleOutput(Executor threadPool) {
        super(threadPool, System.out, new PlainTextProtocol());
    }    
}
