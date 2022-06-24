package finanzmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PasswortVergessenView {

    @FXML
    private TextField altesPasswort;

    @FXML
    private TextField passwortVergessenAntwort;

    @FXML
    private TextField passwortVergessenBenutzername;

    @FXML
    private Button quitPasswortView;

    @FXML
    void quitPasswortVergessen(ActionEvent event) {
        Stage stage = (Stage) quitPasswortView.getScene().getWindow();
        stage.close();
    }

    @FXML
    void okBtnPressed(ActionEvent event) {

    }

}