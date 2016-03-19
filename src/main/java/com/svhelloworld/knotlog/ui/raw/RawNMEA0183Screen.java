package com.svhelloworld.knotlog.ui.raw;

import java.net.URL;
import java.util.ResourceBundle;

import com.svhelloworld.knotlog.Context;
import com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * A screen to display raw NMEA0183 sentences as they arrive from instruments.
 */
public class RawNMEA0183Screen implements Initializable {

    @FXML
    private ListView<NMEA0183Sentence> rawSentences;

    private RawNMEA0183Presenter presenter;

    /**
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        presenter = Context.getBean(RawNMEA0183Presenter.class);
        rawSentences.itemsProperty().bind(presenter.rawSentencesProperty());
    }
}
