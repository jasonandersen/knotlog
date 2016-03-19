package com.svhelloworld.knotlog.ui.exception;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Displays a dialog giving information about an uncaught exception.
 */
@Component
public class ExceptionDialog {

    @Autowired
    private ExceptionPresenter presenter;

    @PostConstruct
    private void initialize() {
        presenter.displayExceptionProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!oldValue && newValue) {
                    raise();
                }
            }

        });
    }

    /**
     * Raise a modal dialog.
     */
    private void raise() {
        Alert alert = configureAlert();
        Pane expandableContent = configureExpandableContent();
        alert.getDialogPane().setExpandableContent(expandableContent);
        alert.showAndWait();
    }

    /**
     * @return a configured alert box
     */
    private Alert configureAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(presenter.getTitle());
        alert.setHeaderText(presenter.getHeaderText());
        alert.setContentText(presenter.getExceptionDescription());
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(500);
        return alert;
    }

    /**
     * @return a pane containing the expandable content
     */
    private Pane configureExpandableContent() {
        Label label = new Label("The exception stacktrace was:");
        TextArea textArea = new TextArea(presenter.getStackTrace());
        textArea.setEditable(false);
        textArea.setWrapText(false);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setPrefWidth(700);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        return expContent;
    }

}
