package finanzmanager;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

        UserRegistration.setRegistrationData(registrationName,registrationUserName,registrationUserPassword, regsuccsessfulllabel);

    }

    public void validateUserLogin()
    {
        //UserLogin.validateUserLogin(loginName, loginPassword);

        PreparedStatement preparedStatement;
        ResultSet rs;

        try
        {
            JavaPostgres connectNow = new JavaPostgres();
            Connection conDb = connectNow.getConnection();
            preparedStatement = conDb.prepareStatement("SELECT * FROM Userinfo WHERE username = ?");
            preparedStatement.setString(1, loginName.getText());

            rs = preparedStatement.executeQuery();

            boolean hasResults = rs.next();

            if(hasResults) {

                do {
                    // User provided password to validate
                    String providedPassword = loginPassword.getText();

                    // Encrypted and Base64 encoded password read from database
                    String securePassword = rs.getString("password");

                    // Salt value stored in database
                    String salt = rs.getString("passwordsalt");

                    boolean passwordMatch = PasswordEncryption.verifyUserPassword(providedPassword, securePassword, salt);

                    if (passwordMatch) {
                        id = (rs.getInt(1));
                        System.out.println("ID: "+ id);


                        System.out.println("Login success");

                        errorText.setText("Erfolgreich eingeloggt!");
                        Stage stage = (Stage) loginOkBtn.getScene().getWindow();
                        stage.close();
                        Stage primaryStage = new Stage();
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Actualview.fxml")));
                        primaryStage.setTitle("FINANZMANAGER - " + loginName.getText());
                        primaryStage.setScene(new Scene(root, 1082, 726));
                        //primaryStage.initStyle(StageStyle.TRANSPARENT);
                        primaryStage.setResizable(false);
                        primaryStage.show();

                    } else {
                        System.out.println("Login failed");
                        errorText.setText("Falsches Password!");
                        loginPassword.clear();
                    }
                }while (rs.next());

            }


            else {
                System.out.println("No Match found");
                errorText.setText("Benutzername nicht vorhanden!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

