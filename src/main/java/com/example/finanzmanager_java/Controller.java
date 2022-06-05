package com.example.finanzmanager_java;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Button loginokbtn;

    @FXML
    void btnGOClicked(ActionEvent event) throws IOException {
        errortext.setText("Please type in your Username and Password");
        Stage stage = (Stage) loginokbtn.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Actualview.fxml"));
        primaryStage.setTitle("FINANZMANAGER");
        primaryStage.setScene(new Scene(root, 1082, 789 ));
        primaryStage.show();
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void getData(ActionEvent actionEvent) {
        System.out.println(loginname.getText());
        System.out.println(password.getText());

    }

}
