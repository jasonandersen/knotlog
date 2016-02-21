package com.svhelloworld.knotlog.ui.screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svhelloworld.knotlog.boot.KnotlogApplication;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * The UI screen that acts as the root container to layout the interface.
 */
public class RootLayoutScreen implements Initializable {

    private static Logger log = LoggerFactory.getLogger(RootLayoutScreen.class);

    @FXML
    private VBox rootLayout;

    @FXML
    private AnchorPane contentPane;

    public RootLayoutScreen() {
        log.info("constructor");
    }

    /**
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("initializing");
        //load default view into content pane
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(KnotlogApplication.class.getResource("/fxml/CurrentConditions.fxml"));
        try {
            AnchorPane currentConditionsPane = loader.load();
            log.debug("loaded currentConditionsPane successfully {}", currentConditionsPane);
        } catch (IOException e) {
            log.error("Could not initialize root layout screen", e);
            Platform.exit();
        }
    }

    /**
     * Load the current conditions screen.
     */
    @FXML
    public void viewCurrentConditions() {
        log.info("view current conditions screen");
    }

}
