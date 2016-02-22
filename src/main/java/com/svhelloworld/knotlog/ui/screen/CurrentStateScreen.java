package com.svhelloworld.knotlog.ui.screen;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svhelloworld.knotlog.Context;
import com.svhelloworld.knotlog.ui.controller.CurrentStateController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * The UI screen to display the current conditions.
 */
public class CurrentStateScreen implements Initializable {

    private static Logger log = LoggerFactory.getLogger(CurrentStateScreen.class);

    @FXML
    private Label windSpeedLabel;

    @FXML
    private Label waterDepthLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label currentWaterDepth;

    @FXML
    private Label currentWindSpeed;

    @FXML
    private Label currentPosition;

    private CurrentStateController controller;

    /**
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("initializing current conditions screen");
        controller = Context.getBean(CurrentStateController.class);
        currentWaterDepth.textProperty().bind(controller.waterDepthProperty());
        currentWindSpeed.textProperty().bind(controller.windSpeedProperty());
        currentPosition.textProperty().bind(controller.positionProperty());
        windSpeedLabel.textProperty().bind(controller.windSpeedLabelProperty());
        waterDepthLabel.textProperty().bind(controller.waterDepthLabelProperty());
        positionLabel.textProperty().bind(controller.positionLabelProperty());

    }

    /**
     * Kick of the parsing.
     */
    @FXML
    public void startParsing() {
        controller.startParsing();
    }

    @FXML
    public void stopParsing() {
        controller.stopParsing();
    }

}
