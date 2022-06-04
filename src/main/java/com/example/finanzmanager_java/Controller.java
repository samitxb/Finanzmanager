package com.example.finanzmanager_java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private TextField loginname;

    @FXML
    private Label errortext;

    @FXML
    private TextField password;

    @FXML
    void btnGOClicked(ActionEvent event) {
        errortext.setText("Please type in your Username and Password");
    }
}