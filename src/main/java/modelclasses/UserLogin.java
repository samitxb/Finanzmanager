package modelclasses;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class UserLogin {
/*
    public static void validateUserLogin(TextField loginName, PasswordField loginPassword){
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

                        //errorText.setText("Erfolgreich eingeloggt!");
                        //Stage stage = (Stage); //loginOkBtn.getScene().getWindow();
                        //stage.close();
                        Stage primaryStage = new Stage();
                        Parent root = FXMLLoader.load(Objects.requireNonNull(UserLogin.class.getResource("Actualview.fxml")));
                        primaryStage.setTitle("FINANZMANAGER - " + loginName.getText());
                        primaryStage.setScene(new Scene(root, 1082, 726));
                        //primaryStage.initStyle(StageStyle.TRANSPARENT);
                        primaryStage.setResizable(false);
                        primaryStage.show();

                    } else {
                        System.out.println("Login failed");
                        //errorText.setText("Falsches Password!");
                        loginPassword.clear();
                    }
                }while (rs.next());
            }
            else {
                System.out.println("No Match found");
                //errorText.setText("Benutzername nicht vorhanden!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    }

