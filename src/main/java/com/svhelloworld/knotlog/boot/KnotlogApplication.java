package com.svhelloworld.knotlog.boot;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svhelloworld.knotlog.Context;
import com.svhelloworld.knotlog.Path;
import com.svhelloworld.knotlog.exceptions.ExceptionHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Primary start up class.
 */
public class KnotlogApplication extends Application {

    private static Logger log = LoggerFactory.getLogger(KnotlogApplication.class);

    private Stage primaryStage;

    private Pane rootLayout;

    private FXMLLoader loader;

    /**
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage stage) throws Exception {
        log.info("starting up");
        initExceptionHandler();
        this.primaryStage = stage;
        this.primaryStage.setTitle("Knotlog");
        initRootLayout();
    }

    /**
     * Initializes default exception handling for all application threads.
     */
    private void initExceptionHandler() {
        ExceptionHandler handler = Context.getBean(ExceptionHandler.class);
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    /**
     * Initializes the root layout.
     * @throws IOException 
     */
    private void initRootLayout() throws IOException {
        loader = new FXMLLoader();
        loader.setLocation(KnotlogApplication.class.getResource(Path.FXML_ROOT_LAYOUT));
        rootLayout = (Pane) loader.load(); // Show the scene containing the root layout. 
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        log.warn("stopping application");
        Context.shutdown();
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
