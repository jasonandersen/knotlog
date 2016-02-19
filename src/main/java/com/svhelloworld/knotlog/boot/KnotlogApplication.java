package com.svhelloworld.knotlog.boot;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.svhelloworld.knotlog.Context;

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

    private ApplicationContext context;

    /**
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage stage) throws Exception {
        log.info("starting up");

        initContext();
        this.primaryStage = stage;
        this.primaryStage.setTitle("Knotlog");
        initRootLayout();
    }

    /**
     * Initializes Spring's application context.
     */
    private void initContext() {
        context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
        Context.setContext(context);
    }

    /**
     * Initializes the root layout.
     */
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(KnotlogApplication.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = (Pane) loader.load(); // Show the scene containing the root layout. 
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException("initRootLayout crapped out!", e);
        }
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
