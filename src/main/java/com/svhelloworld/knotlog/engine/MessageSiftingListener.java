package com.svhelloworld.knotlog.engine;

import java.util.ArrayList;
import java.util.List;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Sifts through messages as they are discovered before passing
 * them on to other registered message listeners.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
public class MessageSiftingListener implements VesselMessageListener {
    
    /**
     * List of messages sifters to act on messages.
     */
    private List<MessageSifter> sifters;
    /**
     * List of message listeners to pass on sifted messages.
     */
    private List<VesselMessageListener> listeners;
    
    /**
     * Constructor.
     */
    public MessageSiftingListener() {
        sifters = new ArrayList<MessageSifter>();
    }
    
    /**
     * Add a listener to receive sifted message events.
     */
    public void addListener(VesselMessageListener listener) {
        synchronized(listeners) {
            listeners.add(listener);
        }
    }
    
    /**
     * Remove a listener from receiving sifted message events.
     */
    public boolean removeListener(VesselMessageListener listener) {
        boolean out;
        synchronized(listener) {
            out = listeners.remove(listener);
        }
        return out;
    }
    
    /**
     * Clears all listeners.
     */
    public void clearListeners() {
        synchronized(listeners) {
            listeners.clear();
        }
    }
    
    /**
     * @see com.svhelloworld.knotlog.engine.SiftingEngine#addSifters(com.svhelloworld.knotlog.engine.MessageSifter[])
     */
    public void addSifter(MessageSifter sifter) {
        synchronized(sifters) {
            sifters.add(sifter);
        }
    }

    /**
     * @see com.svhelloworld.knotlog.engine.SiftingEngine#removeSifter(com.svhelloworld.knotlog.engine.MessageSifter)
     */
    public boolean removeSifter(MessageSifter sifter) {
        boolean out;
        synchronized(sifters) {
            out = sifters.remove(sifter);
        }
        return out;
    }

    /**
     * @see com.svhelloworld.knotlog.engine.VesselMessageListener#vesselMessagesFound(com.svhelloworld.knotlog.messages.VesselMessage[])
     */
    @Override
    public void vesselMessagesFound(VesselMessage... messages) {
        VesselMessage[] sifted = sift(messages);
        if (sifted.length > 0) {
            for (VesselMessageListener listener : listeners) {
                listener.vesselMessagesFound(messages);
            }
        }
    }

    /**
     * @see com.svhelloworld.knotlog.engine.SiftingEngine#sift(com.svhelloworld.knotlog.messages.VesselMessage[])
     */
    private VesselMessage[] sift(VesselMessage... messages) {
        VesselMessage[] sifted = messages;
        for (MessageSifter sifter : sifters) {
            sifted = sifter.sift(sifted);
        }
        return sifted;
    }

    /**
     * @see com.svhelloworld.knotlog.engine.VesselMessageListener#messageDiscoveryComplete()
     */
    @Override
    public void messageDiscoveryComplete() {
    }

    /**
     * @see com.svhelloworld.knotlog.engine.VesselMessageListener#messageDiscoveryStart()
     */
    @Override
    public void messageDiscoveryStart() {
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
    }

}
