package com.svhelloworld.knotlog.exceptions;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

/**
 * Exception handler for uncaught exceptions. Will grab whatever context is available and ensure
 * the exception event is logged and passed on to any other components listening to this compoent.
 */
public class ExceptionHandler implements SubscriberExceptionHandler, UncaughtExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * Allows other components to listen to exceptions caught by this handler.
     */
    public interface Listener {
        /**
         * An uncaught exception was found.
         * @param event
         */
        void handleException(ExceptionEvent event);
    }

    @Autowired
    private ExceptionEventLogger exceptionLogger;

    private List<Listener> listeners;

    /**
     * Constructor.
     */
    public ExceptionHandler() {
        listeners = new LinkedList<Listener>();
    }

    /**
     * Handle uncaught exceptions coming from the application {@link Thread}.
     * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
     */
    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        Map<String, Object> context = buildContext(thread);
        fireExceptionEvent(exception, context);
    }

    /**
     * Handle exceptions coming from the {@link EventBus}.
     * @see com.google.common.eventbus.SubscriberExceptionHandler#handleException(java.lang.Throwable, com.google.common.eventbus.SubscriberExceptionContext)
     */
    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext exceptionContext) {
        Map<String, Object> context = buildContext(exceptionContext);
        fireExceptionEvent(exception, context);
    }

    /**
     * Create the exception event and fire it off to all listeners.
     * @param exception
     * @param context
     */
    private void fireExceptionEvent(Throwable exception, Map<String, Object> context) {
        ExceptionEvent event = new ExceptionEvent(exception, context);
        exceptionLogger.logExceptionEvent(event);
        for (Listener listener : listeners) {
            listener.handleException(event);
        }
    }

    /**
     * Builds the context for an exception based on a thread.
     * @param thread
     */
    private Map<String, Object> buildContext(Thread thread) {
        Map<String, Object> context = new HashMap<>();
        if (thread == null) {
            log.warn("thread is null, cannot build additional info");
            return context;
        }
        context.put("thread name", thread.getName());
        context.put("thread group name", thread.getThreadGroup().getName());
        return context;
    }

    /**
     * Builds the context for an exception based on an event bus suscriber context.
     * @param exceptionContext
     * @return a map containing additional information about the exception
     */
    private Map<String, Object> buildContext(SubscriberExceptionContext exceptionContext) {
        Map<String, Object> context = new HashMap<>();
        if (exceptionContext == null) {
            log.warn("Context is null, cannot build additional info");
            return context;
        }
        context.put("event", exceptionContext.getEvent());
        context.put("subscriber", exceptionContext.getSubscriber());
        context.put("subscriber method", exceptionContext.getSubscriberMethod());
        return context;
    }

    /**
     * Register listener.
     * @param listener
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

}
