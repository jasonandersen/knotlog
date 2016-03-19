package com.svhelloworld.knotlog.ui.currentstate;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svhelloworld.knotlog.Context;
import com.svhelloworld.knotlog.ui.views.VesselMessageView;

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

    private CurrentStatePresenter presenter;

    /**
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("initializing");
        initPresenter();
        initPresenterPropertyBindings();
    }

    /**
     * Initialize controller.
     */
    private void initPresenter() {
        presenter = Context.getBean(CurrentStatePresenter.class);
    }

    /**
     * Initialize all the bindings between the JavaFX controls and the properties of
     * the presenter.
     */
    private void initPresenterPropertyBindings() {
        messages.itemsProperty().bind(presenter.currentStateMessagesProperty());
        nameColumn.setCellValueFactory(new PropertyValueFactory<VesselMessageView<?>, String>("label"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<VesselMessageView<?>, String>("value"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<VesselMessageView<?>, String>("source"));
    }

}
