package finanzmanager;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class SettingsController {
    @FXML
    private TextField kontostand;

    @FXML
    private Button saveSettingsBtn;

    @FXML
    private TextField settingsNutzernameNeu;

    @FXML
    private TextField settingsPasswortNeu;

    public void einstellungenSpeichern(ActionEvent actionEvent) {
        setKontostand(Float.valueOf(kontostand.getText()));
        Stage stage = (Stage) saveSettingsBtn.getScene().getWindow();
        stage.close();
    }

    public void setKontostand(Float eingabeKontostand)
    {

        String queryKontostand;
        PreparedStatement psKontostand;
        ResultSet rsKontostand;

        try
        {

            JavaPostgres connectNow = new JavaPostgres();
            Connection conDb = connectNow.getConnection();



            queryKontostand = "INSERT INTO Userinfo(Kontostand)VALUES(?)";
            psKontostand = conDb.prepareStatement(queryKontostand);
            rsKontostand = psKontostand.executeQuery();


            while(rsKontostand.next())
            {
                psKontostand.setFloat(1, eingabeKontostand);
            }

            rsKontostand.close();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
