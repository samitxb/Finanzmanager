package finanzmanager;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SettingsController {
    @FXML
    private TextField Kontostand;

    @FXML
    private Button saveSettingsBtn;

    @FXML
    private TextField settingsNutzernameNeu;

    @FXML
    private TextField settingsPasswortNeu;

    public void einstellungenSpeichern(ActionEvent actionEvent) {
        Stage stage = (Stage) saveSettingsBtn.getScene().getWindow();
        stage.close();
    }
}
