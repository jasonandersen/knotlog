package com.svhelloworld.knotlog.events;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Listens for {@link DeadEvent}s from the event bus.
 */
@Component
public class DeadEventListener {

    private static Logger log = LoggerFactory.getLogger(DeadEventListener.class);

    @Autowired
    private EventBus eventBus;

    /**
     * Setup the listener
     */
    @PostConstruct
    private void initialize() {
        eventBus.register(this);
    }

    /**
     * Receives all dead events (events not handled by a registered component in the 
     * event bus.)
     * @param deadEvent
     * @throws RequestIgnoredException when a dead event represented a {@link Request}.
     */
    @Subscribe
    public void handleDeadEvents(DeadEvent deadEvent) {
        Object sourceEvent = deadEvent.getEvent();
        log.warn("dead event discovered: {}", sourceEvent);
        if (sourceEvent instanceof Request<?>) {
            log.error("dead event request: {}", sourceEvent);
        }
    }

}
