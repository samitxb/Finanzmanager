package finanzmanager;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelclasses.UserLogin;

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

        if (!Objects.equals(kontostand.getText(), "")){
            System.out.println("Kontostand:" + kontostand.getText());
            setKontostand(Float.valueOf(kontostand.getText()));
        }

        if(!settingsNutzernameNeu.getText().equals("")){
            System.out.println("Neuer Username:" + settingsNutzernameNeu.getText());
            neuerUserName(settingsNutzernameNeu.getText());
        }

        if(!settingsPasswortNeu.getText().equals("")){
            System.out.println("Neues Passwort:" + settingsPasswortNeu.getText());
            neuesUserPasswort(settingsPasswortNeu.getText());
        }


        Stage stage = (Stage) saveSettingsBtn.getScene().getWindow();
        stage.close();
    }


    public void neuerUserName(String neuUserName)//, String neuUserPassword)
    {
        int id = UserLogin.id;


        PreparedStatement ps;

        JavaPostgres connectNow = new JavaPostgres();
        Connection conDb = connectNow.getConnection();

        String sqlUpdate = "UPDATE userinfo SET username = ? WHERE userid = ?";

        try
        {
            ps = conDb.prepareStatement(sqlUpdate);
            ps.setString(1, neuUserName);
            ps.setInt(2,id);

            ps.executeUpdate();

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void neuesUserPasswort(String neuUserPasswort)
    {
        int id = UserLogin.id;


        PreparedStatement ps;

        JavaPostgres connectNow = new JavaPostgres();
        Connection conDb = connectNow.getConnection();

        String sqlUpdate = "UPDATE userinfo SET password = ?, passwordsalt =? WHERE userid = ?";

        try
        {
            String salt = PasswordEncryption.getSalt(30);
            neuUserPasswort = PasswordEncryption.generateSecurePassword(neuUserPasswort, salt);
            ps = conDb.prepareStatement(sqlUpdate);
            ps.setString(1, neuUserPasswort);
            ps.setString(2, salt);
            ps.setInt(3,id);

            ps.executeUpdate();

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }






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



            String queryKontostand = "UPDATE Userinfo SET kontostand =? WHERE userid =? ";

            psKontostand = conDb.prepareStatement(queryKontostand);
            psKontostand.setFloat(1, eingabeKontostand);
            psKontostand.setInt(2,id);
            psKontostand.executeUpdate();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
