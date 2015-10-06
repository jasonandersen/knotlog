package com.svhelloworld.knotlog.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.engine.MessageRejectedException;
import com.svhelloworld.knotlog.engine.parse.NMEA0183SourceParser;
import com.svhelloworld.knotlog.engine.sources.ClassPathFileSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.messages.MockVesselMessage;
import com.svhelloworld.knotlog.messages.PreparseMessage;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Unit test for the <tt>ThreadedOutput</tt> class.
 * 
 * @author Jason Andersen
 * @since Feb 22, 2010
 *
 */
public class ThreadedMessageOutputTest {

    private ThreadedOutput target;

    private ExecutorService threadPool;

    /**
     * @throws Exception 
     * 
     */
    @Before
    public void setUp() throws Exception {
        threadPool = Executors.newSingleThreadExecutor();
        target = new ConsoleOutputMessageListener(threadPool);
    }

    /**
     * @throws Exception 
     * 
     */
    @After
    public void tearDown() throws Exception {
        threadPool.shutdownNow();
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.ThreadedMessageOutput#vesselMessagesFound(com.svhelloworld.knotlog.messages.VesselMessage[]))}.
     * @throws MessageRejectedException 
     */
    @Test
    public void testRun() throws MessageRejectedException {
        target.messageDiscoveryStart();
        for (int i = 0; i < 250; i++) {
            target.vesselMessagesFound(generateMessage());
        }
        assertEquals(target.getMessageCount(), 250);
        target.messageDiscoveryComplete();
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.output.ThreadedOutput#vesselMessagesFound(com.svhelloworld.knotlog.messages.VesselMessage[])}.
     * @throws MessageRejectedException 
     * @throws InterruptedException 
     */
    @Test
    public void testRunStaggered() throws MessageRejectedException, InterruptedException {
        int sleep;
        Random random = new Random();
        target.messageDiscoveryStart();
        for (int i = 0; i < 25; i++) {
            target.vesselMessagesFound(generateMessage());
            sleep = random.nextInt() % 150;
            sleep = Math.abs(sleep);
            Thread.sleep(sleep);
        }
        assertEquals(target.getMessageCount(), 25);
        target.messageDiscoveryComplete();
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.output.ThreadedOutput#messageDiscoveryStart()}.
     */
    @Test
    public void testMessageDiscoveryStart() {
        //no exception? then we're good
        target.messageDiscoveryStart();
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.output.ThreadedOutput#messageDiscoveryComplete()}.
     */
    @Test
    public void testMessageDiscoveryComplete() {
        //no exception? then we're good
        target.messageDiscoveryComplete();
    }

    /**
     * Ensure listener throws an exception when vesselMessagesFound() is
     * called after messageDiscoveryComplete()
     * Test method for {@link com.svhelloworld.knotlog.output.ThreadedOutput#messageDiscoveryComplete()}.
     */
    @Test
    public void testMessageRejectMessage() {
        target.messageDiscoveryComplete();
        try {
            target.vesselMessagesFound(generateMessage());
            fail("exception not thrown");
        } catch (MessageRejectedException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.output.ThreadedOutput#run()}.
     */
    @Test
    public void testGarminDiag() {
        //setup NMEA0183 parser to a diagnostics file and run console output
        threadPool = Executors.newFixedThreadPool(5);
        PrintStream sysOut = System.out;
        OutputStreamWriter osw = new OutputStreamWriter(sysOut);
        TestTextOutput output = new TestTextOutput(threadPool, new BufferedWriter(osw));

        StreamedSource source = new ClassPathFileSource(
                "com/svhelloworld/knotlog/output/GarminDiagFeedSmall.csv");
        NMEA0183SourceParser parser = new NMEA0183SourceParser();
        parser.setSource(source);
        parser.addMessageListener(output);
        parser.addUnrecognizedMessageListener(output);
        parser.addPreparseListener(output);

        parser.run();
    }

    /**
     * Create a mock vessel message.
     */
    private VesselMessage generateMessage() {
        return new MockVesselMessage();
    }

    /**
     * Test text output
     */
    private class TestTextOutput extends TextOutput {

        /**
         * Constructor.
         * @param threadPool
         * @param writer
         * @param protocol
         */
        public TestTextOutput(Executor threadPool, Writer writer) {
            super(threadPool, writer, new TestOutputProtocol());
        }
        /* can't override these methods since they are final
        @Override
        protected void processMessages() {
            writerLock.lock();
            int size = super.getMessageBuffer().size();
            super.write("[" + getThreadName() + "] TestTextOutput.processMessages() " + size + " messages\n");
            writerLock.unlock();
            super.processMessages();
        }
        
        @Override
        protected void processUnrecMessages() {
            writerLock.lock();
            int size = super.getUnrecogMsgBuffer().size();
            super.write("[" + getThreadName() + "] TestTextOutput.processUnrecMessages() " + size + " messages\n");
            writerLock.unlock();
            super.processUnrecMessages();
        }
        
        @Override
        protected void processPreparseData() {
            writerLock.lock();
            int size = super.getPreparseBuffer().size();
            super.write("[" + getThreadName() + "] TestTextOutput.processPreparseData() " + size + " preparse data\n");
            writerLock.unlock();
            super.processPreparseData();
        }
        */

        private String getThreadName() {
            return Thread.currentThread().getName();
        }
    }

    /**
     * Test output protocol.
     */
    private class TestOutputProtocol implements OutputProtocol {

        /**
         * @see com.svhelloworld.knotlog.output.OutputProtocol#streamClose()
         */
        @Override
        public String streamClose() {
            return "[" + getThreadName() + "] TestOutputProtocol close\n";
        }

        /**
         * @see com.svhelloworld.knotlog.output.OutputProtocol#streamOpen()
         */
        @Override
        public String streamOpen() {
            return "[" + getThreadName() + "] TestOutputProtocol open\n";
        }

        /**
         * @see com.svhelloworld.knotlog.output.OutputProtocol#unrecognizedMessage(com.svhelloworld.knotlog.messages.UnrecognizedMessage)
         */
        @Override
        public String unrecognizedMessage(UnrecognizedMessage message) {
            return "[" + getThreadName() + "] " + BabelFish.localize(message) + "\n";
        }

        /**
         * @see com.svhelloworld.knotlog.output.OutputProtocol#vesselMessage(com.svhelloworld.knotlog.messages.VesselMessage)
         */
        @Override
        public String vesselMessage(VesselMessage message) {
            return "[" + getThreadName() + "] " + BabelFish.localize(message) + "\n";
        }

        /**
         * @see com.svhelloworld.knotlog.output.OutputProtocol#preparseMessage(com.svhelloworld.knotlog.messages.PreparseMessage)
         */
        @Override
        public String preparseMessage(PreparseMessage preparse) {
            return "[" + getThreadName() + "] " + getThreadName();
        }

        private String getThreadName() {
            return Thread.currentThread().getName();
        }

    }

}
