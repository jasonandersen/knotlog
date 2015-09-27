package com.svhelloworld.knotlog.output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

import com.svhelloworld.knotlog.messages.PreparseMessage;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Sends message output to an output stream. <tt>OutputProtocol</tt>
 * objects are used to format the text before writing it to an output
 * stream.
 * 
 * @author Jason Andersen
 * @since Mar 16, 2010
 * @see OutputProtocol
 */
public class TextOutput extends ThreadedOutput {
    
    /**
     * Lock to acquire before calling the writer.
     */
    protected final ReentrantLock writerLock = new ReentrantLock(true); //fairness = true;
    /**
     * Output stream to send message output to.
     */
    protected Writer writer;
    /**
     * Protocol used to format messages.
     */
    protected OutputProtocol protocol;
    
    /**
     * Constructor.
     * @param threadPool thread pool
     * @param stream output stream to write output to
     * @param protocol protocol to format output
     * @throws NullPointerException when stream is null
     * @throws NullPointerException when protocol is null
     */
    public TextOutput(
            final Executor threadPool, 
            final OutputStream stream, 
            final OutputProtocol protocol) {
        
        super(threadPool);
        
        if (stream == null) {
            throw new NullPointerException("output stream cannot be null");
        }
        if (protocol == null) {
            throw new NullPointerException("protocol cannot be null");
        }
        OutputStreamWriter osw = new OutputStreamWriter(stream);
        BufferedWriter writer = new BufferedWriter(osw);
        this.writer = writer;
        this.protocol = protocol;
    }
    
    /**
     * Constructor.
     * @param threadPool thread pool
     * @param writer writer to send output to
     * @param protocol protocol to format output
     * @throws NullPointerException when writer is null
     * @throws NullPointerException when protocol is null
     */
    public TextOutput(
            final Executor threadPool,
            final Writer writer, 
            final OutputProtocol protocol) {
        
        super(threadPool);
        
        if (writer == null) {
            throw new NullPointerException("output writer cannot be null");
        }
        if (protocol == null) {
            throw new NullPointerException("protocol cannot be null");
        }
        this.writer = writer;
        this.protocol = protocol;
    }
    
    /**
     * Constructor. Allows child classes to control initialization of
     * output writer.
     * @param threadPool thread pool
     */
    protected TextOutput(Executor threadPool) {
        super(threadPool);
        /*
         * child classes must setup the protocol and writer
         */
    }

    /**
     * @see com.svhelloworld.knotlog.output.ThreadedOutput#processMessages()
     */
    @Override
    protected final void processMessages() {
        try {
            /*
             * make sure we have the lock for the writer so we don't step over
             * other threads calling the same writer
             */
            writerLock.lock();
            VesselMessage message;
            while ((message = getMessageBuffer().poll()) != null) {
                write(format(message));
            }
        } finally {
            writerLock.unlock();
        }
    }
    
    /**
     * @see com.svhelloworld.knotlog.output.ThreadedMessageOutput#open()
     */
    @Override
    protected final void open() {
        assert writer != null;
        assert protocol != null;
        
        writerLock.lock();
        try {
            write(protocol.streamOpen());
        } finally {
            writerLock.unlock();
        }
    }

    /**
     * @see com.svhelloworld.knotlog.output.ThreadedMessageOutput#close()
     */
    @Override
    protected final void close() {
        writerLock.lock();
        try {
            write(protocol.streamClose());
        } finally {
            closeWriter();
            writerLock.unlock();
        }
    }

    /**
     * Writes to the output stream.
     * @param output
     * @throws OutputException on any IOException thrown.
     */
    protected void write(String output) {
        if (output == null || output.length() == 0) {
            //don't bother writing null or empty output
            return;
        }
        if (!writerLock.isHeldByCurrentThread()) {
            throw new OutputException("writer lock must be held prior to writing");
        }
        try {
            writer.write(output);
        } catch (IOException e) {
            throw new OutputException(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * Formats the message based on the given output protocol
     * @param message message to be formatted
     * @return formatted output
     */
    private String format(VesselMessage message) {
        String out;
        if (message instanceof UnrecognizedMessage) {
            out = protocol.unrecognizedMessage((UnrecognizedMessage)message);
        } else if (message instanceof PreparseMessage) {
            out = protocol.preparseMessage((PreparseMessage)message);
        } else {
            out = protocol.vesselMessage(message);
        }
        return out;
    }

    /**
     * Closes writer object.
     */
    private void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            //ignore exception on close
        }
    }
    
}
