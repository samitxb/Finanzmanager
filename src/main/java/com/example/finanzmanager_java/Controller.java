package com.example.finanzmanager_java;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private TextField loginname;
    @FXML
    private MenuItem closeApp;
    @FXML
    private Label errortext;

    @FXML
    private TextField password;

    @FXML
    void btnGOClicked(ActionEvent event) {
        errortext.setText("Please type in your Username and Password");
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void getData(ActionEvent actionEvent){
        System.out.println(loginname.getText());
        System.out.println(password.getText());
       // JavaPostgreSQL.writeToDatabase(loginname.getText(), password.getText());
    }

}
