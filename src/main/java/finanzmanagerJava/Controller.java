package finanzmanagerJava;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;


public class Controller {
    @FXML
    private TextField loginName;
    @FXML
    private PasswordField loginPassword;


    @FXML
    private MenuItem closeApp;
    @FXML
    private Label errorText;


    @FXML
    private Button loginOkBtn;
    @FXML
    private Button quitBtn;

    @FXML
    private TextField registrationName;
    @FXML
    private TextField registrationUserName;
    @FXML
    private TextField registrationUserPassword;


    //Next Window
    @FXML
    void btnGOClicked(ActionEvent event) throws IOException {
        errorText.setText("Please type in your Username and Password");
        Stage stage = (Stage) loginOkBtn.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Actualview.fxml")));
        primaryStage.setTitle("FINANZMANAGER");
        primaryStage.setScene(new Scene(root, 1082, 789 ));
        primaryStage.show();
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    @FXML
    void quitApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void setRegistrationData(ActionEvent actionEvent) {
        System.out.println(loginName.getText());
        System.out.println(loginPassword.getText());
        JavaPostgres.writeToDatabase(registrationName.getText(), registrationUserName.getText(), registrationUserPassword.getText());
    }

}
