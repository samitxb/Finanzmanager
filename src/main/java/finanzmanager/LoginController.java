package finanzmanager;

import database.GetPostgresData;
import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelclasses.UserLogin;
import modelclasses.UserRegistration;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class LoginController {

    public static int id;
    //--------------Login Register----------------------------------
    @FXML
    private TextField loginName;
    @FXML
    private PasswordField loginPassword;

    @FXML
    private Label errorText;

    @FXML
    private Button loginOkBtn;

    //---------------------------------------------------------------
    //-------------Registration---------------------------------------
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

    //----------------------------------------------------------------
    @FXML
    void btnOKClicked(ActionEvent event) throws IOException{

        if(!loginName.getText().isBlank() && !loginPassword.getText().isBlank())
        {
            System.out.println("Login...");
            validateUserLogin();
        }
        else
        {
            System.out.println("Enter login data");
            errorText.setText("Bitte geben sie ihren Benutzernamen und Passwort ein!");
        }
    }



    public void setRegistrationData(ActionEvent actionEvent){

        UserRegistration.setRegistrationData(registrationName,registrationUserName,registrationUserPassword, registrationUserQuestion, regsuccsessfulllabel);

    }

    public void validateUserLogin() throws IOException {

        boolean match;

        match = UserLogin.validateUserLogin(loginName, loginPassword, errorText);

        if (match){
            System.out.println(match);

                errorText.setText("Erfolgreich eingeloggt!");
                Stage stage = (Stage) loginOkBtn.getScene().getWindow();
                stage.close();
                GetPostgresData.getEinnahmenDatabase();
                GetPostgresData.getDauerauftragDatabase();
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Actualview.fxml")));
                primaryStage.setTitle("FINANZMANAGER - " + loginName.getText());
                primaryStage.setScene(new Scene(root, 1082, 726));
                primaryStage.setResizable(false);
                primaryStage.show();

            }


    }
}

