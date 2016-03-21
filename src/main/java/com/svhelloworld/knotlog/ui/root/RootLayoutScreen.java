package com.svhelloworld.knotlog.ui.root;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svhelloworld.knotlog.Context;
import com.svhelloworld.knotlog.Path;
import com.svhelloworld.knotlog.boot.KnotlogApplication;
import com.svhelloworld.knotlog.ui.NMEA0183Simulator;
import com.svhelloworld.knotlog.ui.UI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    private NMEA0183Simulator simulator;

    /**
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("initializing");
        setContentPane(Path.FXML_RAW_NMEA0183);
        startSimulation();
    }

    /**
     * Sets the content node within the content pane.
     * @param node
     */
    private void setContentPane(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(KnotlogApplication.class.getResource(fxmlPath));
        Node node;
        try {
            node = loader.load();
            AnchorPane.setTopAnchor(node, 5.0);
            AnchorPane.setBottomAnchor(node, 5.0);
            AnchorPane.setLeftAnchor(node, 5.0);
            AnchorPane.setRightAnchor(node, 5.0);
            contentPane.getChildren().clear();
            contentPane.getChildren().add(node);
            log.debug("successfully loaded FXML {}", fxmlPath);
        } catch (IOException e) {
            /*
             * FIXME - pretty sure this aint right
             */
            log.error("Could not initialize root layout screen", e);
            Platform.exit();
        }
    }

    /**
     * Start the NMEA0183 simulation.
     */
    private void startSimulation() {
        simulator = Context.getBean(NMEA0183Simulator.class);
        simulator.start();
    }

    /**
     * This will always throw an exception. Use for testing only!!!
     */
    @FXML
    private void doBadThings() {
        UI.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                throw new UnsupportedOperationException("DON'T CLICK THAT BUTTON FOR FUCK'S SAKE.");
            }
        });
    }

    /**
     * Load the current conditions screen.
     */
    @FXML
    public void viewCurrentConditions() {
        setContentPane(Path.FXML_CURRENT_STATE);
    }

    /**
     * Load the raw NMEA0183 screen.
     */
    @FXML
    public void viewRawNMEA0183() {
        setContentPane(Path.FXML_RAW_NMEA0183);
    }

}
