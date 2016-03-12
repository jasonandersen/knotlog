package com.svhelloworld.knotlog.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

/**
 * Exception handler for the {@link EventBus} that wraps the exception in a {@link ExceptionEvent} along
 * with some additional information about the event.
 */
@Component
public class EventBusExceptionHandler implements SubscriberExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(EventBusExceptionHandler.class);

    /**
     * @see com.google.common.eventbus.SubscriberExceptionHandler#handleException(java.lang.Throwable, com.google.common.eventbus.SubscriberExceptionContext)
     */
    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        log.error("HOLY SHIT");
    }

    @Autowired
    public void setEventBus(EventBus eventBus) {
        log.info("event bus successfully injected!");
    }

}
