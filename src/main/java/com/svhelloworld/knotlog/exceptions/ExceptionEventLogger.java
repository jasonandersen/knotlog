package com.svhelloworld.knotlog.exceptions;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Takes an {@link ExceptionEvent} and writes it out to the log files.
 */
@Service
public class ExceptionEventLogger {

    private static Logger log = LoggerFactory.getLogger(ExceptionEventLogger.class);

    /**
     * Log the exception event.
     * @param event
     */
    public void logExceptionEvent(ExceptionEvent event) {
        logException(event.getException());
        logContext(event.getContext());
    }

    /**
     * Log exception context.
     * @param context
     */
    private void logContext(Map<String, Object> context) {
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Exception context: \n");
            for (String key : context.keySet()) {
                Object value = context.get(key);
                buffer.append(key).append("=").append(value).append(";\n");
            }
            log.warn(buffer.toString());
        } catch (Exception e) {
            log.error("Exception while logging exception context", e);
        }
    }

    /**
     * Log exception.
     * @param exception
     */
    private void logException(Throwable exception) {
        if (exception == null) {
            return;
        }
        String message = String.format("[%s] %s", exception.getClass().getSimpleName(), exception.getMessage());
        log.error(message, exception);
    }
}
