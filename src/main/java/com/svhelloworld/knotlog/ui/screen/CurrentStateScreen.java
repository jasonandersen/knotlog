package com.svhelloworld.knotlog.ui.screen;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svhelloworld.knotlog.Context;
import com.svhelloworld.knotlog.ui.controller.CurrentStateController;
import com.svhelloworld.knotlog.ui.currentstate.VesselMessageView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The UI screen to display the current conditions.
 */
public class CurrentStateScreen implements Initializable {

    private static Logger log = LoggerFactory.getLogger(CurrentStateScreen.class);

    @FXML
    private TableView<VesselMessageView<?>> messages;

    @FXML
    private TableColumn<VesselMessageView<?>, String> nameColumn;

    @FXML
    private TableColumn<VesselMessageView<?>, String> valueColumn;

    @FXML
    private TableColumn<VesselMessageView<?>, String> sourceColumn;

    private CurrentStateController controller;

    /**
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("initializing");
        initController();
        initControllerPropertyBindings();
        startSimulation();
    }

    /**
     * Initialize controller.
     */
    private void initController() {
        controller = Context.getBean(CurrentStateController.class);
    }

    /**
     * Initialize all the bindings between the JavaFX controls and the properties of
     * the controller.
     */
    private void initControllerPropertyBindings() {
        messages.itemsProperty().bind(controller.currentStateMessagesProperty());
        nameColumn.setCellValueFactory(new PropertyValueFactory<VesselMessageView<?>, String>("label"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<VesselMessageView<?>, String>("value"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<VesselMessageView<?>, String>("source"));

    }

    /**
     * Kick off the real-time NMEA0183 simulation.
     */
    public void startSimulation() {
        controller.startSimulation();
    }

    /**
     * Stop the real-time NMEA0183 simulation.
     */
    public void stopSimulation() {
        controller.stopSimulation();
    }

}
