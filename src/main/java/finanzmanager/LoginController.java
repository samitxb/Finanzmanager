/*
 * Klasse dient als Controller für das Login/Registrations Fenster
 */

package finanzmanager;

import database.JavaPostgres;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelclasses.UserLogin;
import modelclasses.UserRegistration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Klasse LoginController ist der Controller für die LoginView.fxml. Also für das Login & Registrations Fenster.
 *
 * @author Max Weichselgartner, Michael Irlmeier
 * @version 1.0
 */
public class LoginController {

    public static int id;
    /*=======================================Login=======================================*/
    @FXML
    private TextField loginName;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Label errorText;
    @FXML
    private Button loginOkBtn;
    @FXML
    private Button passwortVergessenBtn;
    /*=======================================Registrieren=======================================*/
    @FXML
    private TextField registrationName;
    @FXML
    private TextField registrationUserName;
    @FXML
    private TextField registrationUserPassword;
    @FXML
    private Label regsuccsessfulllabel;
    @FXML
    private TextField registrationUserQuestion;


    /**
     * Beim Aufrufen der Funktion wird der volle Name des Benutzers aus der Datenbank geholt.
     *
     * @return Gibt den vollen Namen des Benutzers zurück.
     * @throws SQLException -> wirft einen Fehler.
     */
    public static String getUserFullname() throws SQLException {
        String nameOfUser = "";
        int id = UserLogin.id;
        Connection con = JavaPostgres.databaseConnectionLink;

        PreparedStatement pst = con.prepareStatement("SELECT * FROM userinfo WHERE userid=?");
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            nameOfUser = rs.getString("fullname");
        }
        rs.close();
        return nameOfUser;
    }

    /**
     * Fragt ab, ob die Textfelder leer sind, falls nicht, wird die Funktion validateUserLogin aufgerufen.
     *
     * @param event -> Funktion wird beim Drücken des Knopfes ausgeführt.
     * @throws IOException -> wirft einen Fehler.
     */
    @FXML
    void btnOKClicked(ActionEvent event) throws IOException, SQLException {
        if (!loginName.getText().isBlank() && !loginPassword.getText().isBlank()) {
            System.out.println("Login...");
            validateUserLogin();
        } else {
            System.out.println("Enter login data");
            errorText.setText("Bitte geben sie ihren Benutzernamen und Passwort ein!");
        }
    }

    /**
     * Übergibt die Parameter an die Funktion setRegistrationData in der Klasse UserRegistration
     *
     * @param actionEvent -> Funktion wird beim Drücken des Knopfes ausgeführt.
     */
    public void setRegistrationData(ActionEvent actionEvent) {
        UserRegistration.setRegistrationData(registrationName, registrationUserName, registrationUserPassword, registrationUserQuestion, regsuccsessfulllabel);
    }

    /**
     * Wenn Passwort und Benutzername übereinstimmen, wird man zur ActualView weitergeleitet.
     *
     * @throws IOException -> wirft einen Fehler.
     */
    public void validateUserLogin() throws IOException, SQLException {
        boolean match;

        match = UserLogin.validateUserLogin(loginName, loginPassword, errorText);

        if (match) {
            System.out.println(match);
            errorText.setText("Erfolgreich eingeloggt!");
            Stage stage = (Stage) loginOkBtn.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ActualView.fxml")));
            primaryStage.setTitle("FINANZMANAGER - " + getUserFullname());
            primaryStage.setScene(new Scene(root, 1082, 737));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }

    /**
     * Öffnet das Fenster, in der ein Benutzer sein Passwort zurücksetzten kann.
     *
     * @param actionEvent -> Funktion wird beim Drücken des Knopfes ausgeführt.
     * @throws IOException -> wirft einen Fehler.
     */
    public void goToPasswortVergessen(ActionEvent actionEvent) throws IOException {
        Stage passwortStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PasswortVergessenView.fxml")));
        passwortStage.setTitle("Passwort Vergessen - " + loginName.getText());
        passwortStage.setScene(new Scene(root, 300, 400));
        passwortStage.setResizable(false);
        passwortStage.initModality(Modality.APPLICATION_MODAL);
        passwortStage.show();
    }
}
