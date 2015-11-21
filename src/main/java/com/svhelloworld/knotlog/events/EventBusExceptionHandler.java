package com.svhelloworld.knotlog.events;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

/**
 * Exception handler for Guava {@link EventBus} that passes on all exceptions.
 */
public class EventBusExceptionHandler implements SubscriberExceptionHandler {

    /**
     * @see com.google.common.eventbus.SubscriberExceptionHandler#handleException(java.lang.Throwable, com.google.common.eventbus.SubscriberExceptionContext)
     */
    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        if (exception instanceof RuntimeException) {
            throw (RuntimeException) exception;
        }
        throw new EventBusException(exception);
    }

}
