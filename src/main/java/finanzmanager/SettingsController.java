/*
 * Diese Klasse dient als Controller für das Einstellungsfenster
 */

package finanzmanager;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelclasses.NurNummern;
import modelclasses.UserLogin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static modelclasses.NurNummern.runden;


/**
 * Klasse SettingsController ist der Controller für das Einstellungsfenster.
 *
 * @author Max Weichselgartner, Michael Irlmeier
 * @version 1.0
 */
public class SettingsController implements Initializable {
    @FXML
    private TextField kontostand;
    @FXML
    private Button saveSettingsBtn;
    @FXML
    private TextField settingsNutzernameNeu;
    @FXML
    private TextField settingsPasswortNeu;
    @FXML
    private Label settingsKontostandLabel;
    @FXML
    private Button quitSettingsBtn;
    @FXML
    private Label settingsPasswortLabel;
    @FXML
    private Label settingsNutzernameLabel;

    /**
     * Beim start des Controllers werden diese Funktionen ausgeführt.
     *
     * @param url            .
     * @param resourceBundle .
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        NurNummern.numericOnly(kontostand);
    }

    /**
     * Schließt das Fenster Einstellungen.
     *
     * @param event -> Funktion wird beim Drücken des Knopfes ausgeführt.
     */
    @FXML
    void quitSettings(ActionEvent event) {
        Stage stage = (Stage) quitSettingsBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Funktion wird ausgeführt, wenn man auf Speichern drückt.
     * Leitet weiter zu neuerUserName, neuesUserPasswort und setKontostand.
     *
     * @param actionEvent -> Funktion wird beim Drücken des Knopfes ausgeführt.
     * @throws SQLException -> wirft einen Fehler.
     */
    public void einstellungenSpeichern(ActionEvent actionEvent) throws SQLException {
        boolean istdouble;
        istdouble = NurNummern.istDouble(kontostand.getText());

        settingsKontostandLabel.setText(null);

        if (!Objects.equals(kontostand.getText(), "")) {
            if (istdouble) {
                System.out.println("Kontostand:" + kontostand.getText());
                setKontostand(runden(Float.parseFloat(kontostand.getText()), 2));
                settingsKontostandLabel.setText("Gespeichert!");
            } else settingsKontostandLabel.setText("Keine Zahl!");
        }
        if (!settingsNutzernameNeu.getText().equals("")) {
            System.out.println("Neuer Username:" + settingsNutzernameNeu.getText());
            neuerUserName(settingsNutzernameNeu.getText());
        }
        if (!settingsPasswortNeu.getText().equals("")) {
            System.out.println("Neues Passwort:" + settingsPasswortNeu.getText());
            neuesUserPasswort(settingsPasswortNeu.getText());
        }
    }

    /**
     * Updatet den UserName des eingeloggten Benutzers
     *
     * @param neuUserName übergibt den gewünschten Benutzernamen.
     * @throws SQLException -> wirft einen Fehler.
     */
    public void neuerUserName(String neuUserName) throws SQLException {
        int id = UserLogin.id;

        PreparedStatement ps;
        PreparedStatement psCheckUser;
        ResultSet resultSet;
        JavaPostgres connectNow = new JavaPostgres();
        Connection conDb = connectNow.getConnection();

        psCheckUser = conDb.prepareStatement("SELECT * FROM userinfo WHERE username=?");
        psCheckUser.setString(1, neuUserName);
        resultSet = psCheckUser.executeQuery();
        try {
            // Checkt, ob der neue Username in der Datenbank existiert
            if (resultSet.isBeforeFirst()) {
                System.out.println("Nutzername vergeben");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Nutzername vergeben! Bitte anderen Nutzernamen wählen.");
                // Wirft Fenster mit Fehlermeldung aus
                alert.show();
                settingsNutzernameLabel.setTextFill(Color.color(1, 0, 0));
                settingsNutzernameLabel.setText("Vergeben!");
            } else {
                ps = conDb.prepareStatement("UPDATE userinfo SET username = ? WHERE userid = ?");
                ps.setString(1, neuUserName);
                ps.setInt(2, id);
                ps.executeUpdate();
                settingsNutzernameLabel.setTextFill(Color.color(1, 1, 1));
                settingsNutzernameLabel.setText("Gespeichert!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setzt ein neues Passwort des eingeloggten Benutzers.
     *
     * @param neuUserPasswort übergibt das gewünschte Passwort.
     */
    public void neuesUserPasswort(String neuUserPasswort) {
        int id = UserLogin.id;

        PreparedStatement ps;
        JavaPostgres connectNow = new JavaPostgres();
        Connection conDb = connectNow.getConnection();
        try {
            String salt = PasswordEncryption.getSalt(30);
            neuUserPasswort = PasswordEncryption.generateSecurePassword(neuUserPasswort, salt);
            ps = conDb.prepareStatement("UPDATE userinfo SET password = ?, passwordsalt =? WHERE userid = ?");
            ps.setString(1, neuUserPasswort);
            ps.setString(2, salt);
            ps.setInt(3, id);

            ps.executeUpdate();
            settingsPasswortLabel.setText("Gespeichert!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setzt den gewünschten Kontostand des eingeloggten Benutzers.
     *
     * @param eingabeKontostand übergibt den gewünschten Kontostand.
     */
    public void setKontostand(Float eingabeKontostand) {
        int id = UserLogin.id;

        JavaPostgres connectNow = new JavaPostgres();
        Connection conDb = connectNow.getConnection();
        try {
            PreparedStatement psKontostand;

            psKontostand = conDb.prepareStatement("UPDATE Userinfo SET kontostand =? WHERE userid =? ");
            psKontostand.setFloat(1, eingabeKontostand);
            psKontostand.setInt(2, id);
            psKontostand.executeUpdate();
            settingsKontostandLabel.setText("Gespeichert!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
