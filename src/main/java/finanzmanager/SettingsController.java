package finanzmanager;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelclasses.NurNummern;
import modelclasses.Uebersicht;
import modelclasses.UserLogin;

import java.sql.*;
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

    @FXML
    private Label kontostandLabel;

    @FXML
    private Button quitSettingsBtn;

    /**
     * Schließt das Fenster
     * @param event -> Funktion wird beim drücken des Knopfes ausgeführt.
     */
    @FXML
    void quitSettings(ActionEvent event) {

        Stage stage = (Stage) quitSettingsBtn.getScene().getWindow();
        stage.close();

    }

    /**
     * Funktion wird ausgeführt, wenn man auf Speichern drückt.
     * Leitet weiter zu neuerUserName, neuesUserPasswort und setKontostand.
     * @param actionEvent -> Funktion wird beim drücken des Knopfes ausgeführt.
     * @throws SQLException wirft einen Fehler.
     */
    public void einstellungenSpeichern(ActionEvent actionEvent) throws SQLException {

        boolean numerisch;


        kontostandLabel.setText(null);

        if (!Objects.equals(kontostand.getText(), "")){
            numerisch = NurNummern.isNumeric(kontostand.getText());
            if (numerisch){
                System.out.println("Kontostand:" + kontostand.getText());
                setKontostand(Float.valueOf(kontostand.getText()));
                kontostandLabel.setText("Gespeichert!");
            }else kontostandLabel.setText("Keine Zahl!");
        }

        if(!settingsNutzernameNeu.getText().equals("")){
            System.out.println("Neuer Username:" + settingsNutzernameNeu.getText());
            neuerUserName(settingsNutzernameNeu.getText());
        }

        if(!settingsPasswortNeu.getText().equals("")){
            System.out.println("Neues Passwort:" + settingsPasswortNeu.getText());
            neuesUserPasswort(settingsPasswortNeu.getText());
        }


    }

    /**
     * Updatet den UserName des eingeloggten Benutzers
     * @param neuUserName übergibt den gewünschten Benutzernamen.
     * @throws SQLException wirft einen Fehler.
     */
    public void neuerUserName( String neuUserName) throws SQLException//, String neuUserPassword)
    {
        int id = UserLogin.id;


        PreparedStatement ps;
        PreparedStatement psCheckUser;
        ResultSet resultSet;

        JavaPostgres connectNow = new JavaPostgres();
        Connection conDb = connectNow.getConnection();


        psCheckUser = conDb.prepareStatement("SELECT * FROM userinfo WHERE username=?");
        psCheckUser.setString(1, neuUserName);
        resultSet = psCheckUser.executeQuery();


        try
        {

            // Checkt, ob der neue Username in der Datenbank existiert
            if(resultSet.isBeforeFirst())
            {
                System.out.println("Nutzername vergeben");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Nutzername vergeben! Bitte anderen Nutzernamen wählen.");

                // Wirft Fenster mit Fehlermeldung aus
                alert.show();
            }

            else
            {
                ps = conDb.prepareStatement("UPDATE userinfo SET username = ? WHERE userid = ?");
                ps.setString(1, neuUserName);
                ps.setInt(2, id);
                ps.executeUpdate();
            }

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Setzt ein neues Passwort des eingeloggten Benutzers.
     * @param neuUserPasswort übergibt das gewünschte Passwort.
     */
    public void neuesUserPasswort(String neuUserPasswort)
    {
        int id = UserLogin.id;


        PreparedStatement ps;

        JavaPostgres connectNow = new JavaPostgres();
        Connection conDb = connectNow.getConnection();

        try
        {
            String salt = PasswordEncryption.getSalt(30);
            neuUserPasswort = PasswordEncryption.generateSecurePassword(neuUserPasswort, salt);
            ps = conDb.prepareStatement("UPDATE userinfo SET password = ?, passwordsalt =? WHERE userid = ?");
            ps.setString(1, neuUserPasswort);
            ps.setString(2, salt);
            ps.setInt(3,id);

            ps.executeUpdate();

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Setzt den gewünschten Kontostand des eingeloggten Benutzers.
     * @param eingabeKontostand übergibt den gewünschten Kontostand.
     */
    public void setKontostand(Float eingabeKontostand)
    {
        int id = UserLogin.id;


        System.out.println(eingabeKontostand);
        System.out.println("method: "+ kontostand.getText());

        JavaPostgres connectNow = new JavaPostgres();
        Connection conDb = connectNow.getConnection();

        try
        {
            PreparedStatement psKontostand;

            psKontostand = conDb.prepareStatement("UPDATE Userinfo SET kontostand =? WHERE userid =? ");
            psKontostand.setFloat(1, eingabeKontostand);
            psKontostand.setInt(2,id);
            psKontostand.executeUpdate();

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
