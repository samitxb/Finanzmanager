package finanzmanager;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class LoginController {
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
            errorText.setText("Please type in your Username and Password");
        }
    }



    public void setRegistrationData(ActionEvent actionEvent){

        System.out.println(registrationUserName.getText());
        System.out.println(registrationUserPassword.getText());

        if (Objects.equals(registrationName.getText(), ""))
        {
            regsuccsessfulllabel.setText("No Name!");
        }
        else if(Objects.equals(registrationUserName.getText(), "")) {
            regsuccsessfulllabel.setText("No Login Name!");
        }
        else if (Objects.equals(registrationUserPassword.getText(), "")) {
            regsuccsessfulllabel.setText("No Password!");
        } else {
            JavaPostgres.writeToDatabase(registrationName.getText(), registrationUserName.getText(), registrationUserPassword.getText());
            registrationName.clear();
            registrationUserName.clear();
            registrationUserPassword.clear();
        }
    }

    public void validateUserLogin()
    {
        PreparedStatement preparedStatement;
        ResultSet rs;

        try
        {
            JavaPostgres connectNow = new JavaPostgres();
            Connection conDb = connectNow.getConnection();
            preparedStatement = conDb.prepareStatement("SELECT username, password, passwordsalt FROM Userinfo WHERE username = ?");
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
                        System.out.println("Login success");

                        errorText.setText("Success");
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
                        errorText.setText("Please type in right Password");
                        loginPassword.clear();
                    }
                }while (rs.next());
            }
            else {
                System.out.println("No Match found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

