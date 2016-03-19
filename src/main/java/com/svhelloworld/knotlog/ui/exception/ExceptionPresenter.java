package com.svhelloworld.knotlog.ui.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.svhelloworld.knotlog.exceptions.ExceptionEvent;
import com.svhelloworld.knotlog.exceptions.ExceptionHandler;
import com.svhelloworld.knotlog.i18n.BabelFish;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Presenter for the {@link ExceptionScreen}. Any exception that gets thrown by either an
 * event bus subscriber or on any application thread will captured by the {@link ExceptionHandler}.
 * This class will listen for any exceptions. When they occur, it will populate it's properties
 * with information about that exception. The {@link ExceptionScreen} can then display an error
 * dialog and bind to those properties.
 */
@Component
public class ExceptionPresenter implements ExceptionHandler.Listener {

    private static Logger log = LoggerFactory.getLogger(ExceptionPresenter.class);

    @Autowired
    private ExceptionHandler exceptionHandler;

    private Throwable currentException;

    private final BooleanProperty displayException;

    /**
     * Constructor
     */
    public ExceptionPresenter() {
        displayException = new SimpleBooleanProperty(false);
    }

    @PostConstruct
    public void initialize() {
        log.info("listening to the exception handler");
        exceptionHandler.addListener(this);
    }

    /**
     * @see com.svhelloworld.knotlog.exceptions.ExceptionHandler.Listener#handleException(com.svhelloworld.knotlog.exceptions.ExceptionEvent)
     */
    @Override
    public void handleException(ExceptionEvent event) {
        log.warn("handling exception");
        this.currentException = event.getException();
        displayException.set(true);
    }

    /**
     * Clears out the exception and resets the display flag.
     */
    public void clearException() {
        log.info("clearing out exception");
        currentException = null;
        displayException.set(false);
    }

    /*
     * Accessor methods
     */

    public Throwable getCurrentException() {
        return currentException;
    }

    public boolean getDisplayException() {
        return displayException.get();
    }

    public BooleanProperty displayExceptionProperty() {
        return displayException;
    }

    public String getTitle() {
        return BabelFish.localizeKey("exception.dialog.title");
    }

    public String getHeaderText() {
        return BabelFish.localizeKey("exception.dialog.header");
    }

    public String getExceptionDescription() {
        String message = currentException == null ? "" : currentException.getMessage();
        String checkLogFiles = BabelFish.localizeKey("exception.dialog.check.logs");
        return String.format("%s\n%s", message, checkLogFiles);
    }

    public String getStackTrace() {
        if (currentException == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        currentException.printStackTrace(pw);
        return sw.toString();

    }

}
