package com.svhelloworld.knotlog.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

/**
 * Handles logging for exceptions that occur within the {@link EventBus}.
 */
public class LoggingEventBusExceptionHandler implements SubscriberExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(LoggingEventBusExceptionHandler.class);

    /**
     * @see com.google.common.eventbus.SubscriberExceptionHandler#handleException(java.lang.Throwable, com.google.common.eventbus.SubscriberExceptionContext)
     */
    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        StringBuffer message = new StringBuffer();
        buildExceptionMessage(exception, message);
        buildContextMessage(context, message);
        log.error(message.toString(), exception);
    }

    /**
     * @param exception
     * @param message
     */
    private void buildExceptionMessage(Throwable exception, StringBuffer message) {
        if (exception == null) {
            return;
        }
        message.append("[").append(exception.getClass().getName()).append("] ");
        message.append(exception.getMessage());
        if (exception.getCause() != null) {
            Throwable cause = exception.getCause();
            message.append("\n");
            message.append("Cause: [").append(cause.getClass().getName()).append("] ");
            message.append(cause.getMessage());
        }
    }

    /**
     * @param context
     * @param message
     */
    private void buildContextMessage(SubscriberExceptionContext context, StringBuffer message) {
        if (context == null) {
            return;
        }
        message.append("\n").append("Event: [").append(context.getEvent().getClass().getName()).append("] ");
        message.append(context.getEvent().toString());
        message.append("\n").append("Subscriber: [").append(context.getSubscriber().getClass().getName()).append("] ");
        message.append(context.getSubscriber().toString());
        message.append("\n").append("Method: [").append(context.getSubscriberMethod().toString()).append("]\n");
    }

}
