/*
 * Klasse verwaltet den User-Login
 */
package modelclasses;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * Klasse UserLogin überprüft, ob Benutzername und Passwort übereinstimmen.
 *
 * @author Max Weichselgartner, Michael Irlmeier
 * @version 1.0
 */
public class UserLogin {
    public static int id;


    /**
     * Überprüft, ob Benutzername und Passwort übereinstimmen.
     *
     * @param loginName     ist der eingegebene LoginName
     * @param loginPassword ist das eingegebene Passwort
     * @param errorText     ist das Label
     * @return true, falls Passwort und Benutzername übereinstimmen
     */
    public static boolean validateUserLogin(TextField loginName, TextField loginPassword, Label errorText) {
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            JavaPostgres connectNow = new JavaPostgres();
            Connection conDb = connectNow.getConnection();
            preparedStatement = conDb.prepareStatement("SELECT * FROM Userinfo WHERE username = ?");
            preparedStatement.setString(1, loginName.getText());

            rs = preparedStatement.executeQuery();
            boolean hasResults = rs.next();
            if (hasResults) {

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
                        System.out.println("ID: " + id);
                        System.out.println("Login success");

                        return true;
                    } else {
                        System.out.println("Login failed");
                        errorText.setText("Falsches Password!");
                        loginPassword.clear();
                    }
                } while (rs.next());

            } else {
                System.out.println("No Match found");
                errorText.setText("Benutzername nicht vorhanden!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}



