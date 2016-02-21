package com.svhelloworld.knotlog.boot;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        this.primaryStage = stage;
        this.primaryStage.setTitle("Knotlog");
        initRootLayout();
    }

    /**
     * Initializes the root layout.
     */
    private void initRootLayout() {
        try {
            loader = new FXMLLoader();
            loader.setLocation(KnotlogApplication.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = (Pane) loader.load(); // Show the scene containing the root layout. 
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            log.error("initRootLayout() method crapped out", e);
            throw new RuntimeException("initRootLayout crapped out!", e);
        }
    }

    @Override
    public void stop() {
        /*
         * TODO - Grab all beans out of the Spring context that implement Closeable. Iterate through 
         * them all and call their close() method.
         */
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
