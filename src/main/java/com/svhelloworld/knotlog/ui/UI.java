package com.svhelloworld.knotlog.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;

/**
 * Some user interface utility methods.
 */
public class UI {

    private static Logger log = LoggerFactory.getLogger(UI.class);

    /**
     * Private constructor - no instantiation
     */
    private UI() {
        //noop
    }

    /**
     * Runs a task on a background UI thread. If the UI framework is not initialized, the task
     * will be run on the calling thread.
     * @param task
     */
    public static void runOnUIThread(Runnable task) {
        if (task == null) {
            log.warn("cannot run task on UI thread, task == null");
            return;
        }
        try {
            /*
             * try to run this on a JavaFX background thread
             */
            Platform.runLater(task);
        } catch (IllegalStateException e) {
            log.warn("JavaFX framework not initialized - running task on calling thread");
            /*
             * This means the JavaFX toolkit hasn't been booted up. Most likely, we're running
             * unit tests against the controller so just set the property value outside of the
             * JavaFX threading.
             */
            task.run();
        }
    }

}
