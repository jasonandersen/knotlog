package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.engine.MessageRejectedException;
import com.svhelloworld.knotlog.engine.PreparseListener;
import com.svhelloworld.knotlog.engine.UnrecognizedMessageListener;
import com.svhelloworld.knotlog.engine.VesselMessageListener;
import com.svhelloworld.knotlog.engine.parse.NMEA0183Parser;
import com.svhelloworld.knotlog.engine.sources.ClassPathFileSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.messages.PreparseMessage;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Unit test for NMEA0183Parser class.
 * 
 * @author Jason Andersen
 * @since Feb 23, 2010
 *
 */
public class NMEA0183ParserTest {
    
    private static final Locale jvmLocale;
    
    /**
     * Make sure we can get back to the JVM default locale
     */
    static {
        jvmLocale = Locale.getDefault();
    }
    
    private static final String TEST_FEED = 
        "com/svhelloworld/knotlog/engine/parse/NMEA0183TestFeed.csv";

    private static final String GARMIN_DIAG_FEED = 
        "com/svhelloworld/knotlog/engine/parse/GarminDiagFeed.csv";
    
    private static final String GARMIN_DIAG_CORRUPTED = 
        "com/svhelloworld/knotlog/engine/parse/GarminDiagFeedCorruptedData.csv";
    
    private NMEA0183Parser target;
    
    private StreamedSource source;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setup() throws Exception {
        //reset the default locale
        Locale.setDefault(jvmLocale);
        //instantiate
        target = new NMEA0183Parser();
        source = new ClassPathFileSource(TEST_FEED);
        target.setSource(source);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        source.close();
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Parser#parse()}.
     */
    @Test
    public void testRun() {
        target.run();
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Parser#parse()}.
     */
    @Test
    public void testMessageListeners() {
        VesselMessageListener listener = new MessageConsoleListener();
        target.addMessageListener(listener);
        target.run();
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Parser#parse()}.
     */
    @Test
    public void testUnrecognizedMessageListeners() {
        UnrecognizedMsgConsoleListener listener = new UnrecognizedMsgConsoleListener();
        target.addUnrecognizedMessageListener(listener);
        target.run();
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Parser#parse()}.
     */
    @Test
    public void testPreparseListeners() {
        PreparseConsoleListener listener = new PreparseConsoleListener();
        target.addPreparseListener(listener);
        target.run();
        assertEquals(7, listener.eventsReceived);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Parser#parse()}.
     */
    @Test
    public void testGarminDiagnosticsFeed() {
        target = new NMEA0183Parser();
        target.setSource(new ClassPathFileSource(GARMIN_DIAG_FEED));
        MessageConsoleListener messages = new MessageConsoleListener();
        PreparseConsoleListener preparse = new PreparseConsoleListener();
        UnrecognizedMsgConsoleListener unrecognizedMessages = new UnrecognizedMsgConsoleListener();
        target.addMessageListener(messages);
        target.addPreparseListener(preparse);
        target.addUnrecognizedMessageListener(unrecognizedMessages);
        target.run();
        
        assertEquals(111501, preparse.eventsReceived);
        assertEquals(99662, messages.eventsReceived);
        assertEquals(90959, unrecognizedMessages.eventsReceived);
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Parser#parse()}.
     */
    @Test
    public void testGarminDiagnosticsFeedEspanol() {
        //let's see what happens in spanish
        Locale.setDefault(new Locale("es"));
        target = new NMEA0183Parser();
        target.setSource(new ClassPathFileSource(GARMIN_DIAG_FEED));
        target.addMessageListener(new MessageConsoleListener());
        target.run();
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.NMEA0183Parser#parse()}.
     */
    @Test
    public void testGarminDiagnosticsFeedCorrupted() {
        /*
         * this feed was taken while we were having problems with 
         * corrupted NMEA input. Make sure we can handle this stuff.
         */
        target = new NMEA0183Parser();
        target.setSource(new ClassPathFileSource(GARMIN_DIAG_CORRUPTED));
        
        MessageConsoleListener messages = new MessageConsoleListener();
        PreparseConsoleListener preparse = new PreparseConsoleListener();
        UnrecognizedMsgConsoleListener unrecognizedMessages = new UnrecognizedMsgConsoleListener();
        
        target.addMessageListener(messages);
        target.addPreparseListener(preparse);
        target.addUnrecognizedMessageListener(unrecognizedMessages);
        target.run();
        
        assertEquals(35392, preparse.eventsReceived);
        assertEquals(37743, messages.eventsReceived);
        assertEquals(23558, unrecognizedMessages.eventsReceived);
    }
    
    /**
     * Listen for new messages parsed and display them on the console.
     */
    private class MessageConsoleListener implements VesselMessageListener {
        
        private int eventsReceived = 0;
        
        /**
         * @see com.svhelloworld.knotlog.engine.VesselMessageListener#messageDiscoveryComplete()
         */
        @Override
        public void messageDiscoveryComplete() {
            //System.out.println("MessageConsoleListener: " + eventsReceived + " events received");
            //System.out.println("MessageConsoleListener: *** COMPLETE ***");
        }

        /**
         * @see com.svhelloworld.knotlog.engine.VesselMessageListener#messageDiscoveryStart()
         */
        @Override
        public void messageDiscoveryStart() {
            //System.out.println("MessageConsoleListener: *** START ***");
        }

        /**
         * @see com.svhelloworld.knotlog.engine.VesselMessageListener#vesselMessagesFound(com.svhelloworld.knotlog.messages.VesselMessage[])
         */
        @Override
        public void vesselMessagesFound(VesselMessage... messages) throws MessageRejectedException {
            for (@SuppressWarnings("unused") VesselMessage message : messages) {
                //System.out.println("MessageConsoleListener: " + message);
                eventsReceived++;
            }
        }

        /**
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
        }
        
    }
    
    private class UnrecognizedMsgConsoleListener implements UnrecognizedMessageListener {
        
        int eventsReceived = 0;
        
        /**
         * @see com.svhelloworld.knotlog.engine.UnrecognizedMessageListener#unrecognizedMessagesFound(com.svhelloworld.knotlog.messages.UnrecognizedMessage[])
         */
        @Override
        public void unrecognizedMessagesFound(UnrecognizedMessage... messages) {
            for (@SuppressWarnings("unused") UnrecognizedMessage message : messages) {
                //System.out.println("UnrecognizedMsgConsoleListener: " + message);
                eventsReceived++;
            }
        }

        /**
         * @see com.svhelloworld.knotlog.engine.MessageDiscoveryListener#messageDiscoveryComplete()
         */
        @Override
        public void messageDiscoveryComplete() {
            //System.out.println("UnrecognizedMsgConsoleListener: " + eventsReceived + " events recieved");
            //System.out.println("UnrecognizedMsgConsoleListener: *** COMPLETE ***");
        }

        /**
         * @see com.svhelloworld.knotlog.engine.MessageDiscoveryListener#messageDiscoveryStart()
         */
        @Override
        public void messageDiscoveryStart() {
            //System.out.println("UnrecognizedMsgConsoleListener: *** START ***");
        }
        
    }
    
    /**
     * Listen for preparse events and toss them to the console.
     */
    private class PreparseConsoleListener implements PreparseListener {
        
        int eventsReceived = 0;

        /**
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
        }

        /**
         * @see com.svhelloworld.knotlog.engine.MessageDiscoveryListener#messageDiscoveryComplete()
         */
        @Override
        public void messageDiscoveryComplete() {
            //System.out.println("PreparseConsoleListener: " + eventsReceived + " events received");
            //System.out.println("PreparseConsoleListener: *** COMPLETE ***");
        }

        /**
         * @see com.svhelloworld.knotlog.engine.MessageDiscoveryListener#messageDiscoveryStart()
         */
        @Override
        public void messageDiscoveryStart() {
            //System.out.println("PreparseConsoleListener: *** START ***");
        }
        
        /**
         * @see com.svhelloworld.knotlog.engine.PreparseListener#preparsedInput(java.lang.String)
         */
		@Override
		public void preparsedInput(PreparseMessage message) {
			eventsReceived++;
		}
        
    }
}
