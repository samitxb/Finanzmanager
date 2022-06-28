package modelclasses;

import database.JavaPostgres;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Objects;

/**
 * Klasse UserRegistration setzt die Registration Data
 *
 * @author Max Weichselgartner
 * @version 1.0
 *
 */
public class UserRegistration {

    /**
     * Die Übergebenen Parameter werden überprüft, ob sie leer sind, falls nicht werden die Daten an die Datenbank weitergegeben.
     * @param registrationName ist der vollständige Name
     * @param registrationUserName ist der Benutzername
     * @param registrationUserPassword ist das Passwort
     * @param registrationUserQuestion ist die Sicherheitsfragenantwort
     * @param regsuccsessfulllabel ist das Label
     */
    public static void setRegistrationData(TextField registrationName, TextField registrationUserName, TextField registrationUserPassword, TextField registrationUserQuestion ,Label regsuccsessfulllabel){

        System.out.println(registrationUserName.getText());
        System.out.println(registrationUserPassword.getText());

        if (Objects.equals(registrationName.getText(), ""))
        {
            System.out.println("Kein Name!");
            regsuccsessfulllabel.setText("Kein Name!");
        }
        else if(Objects.equals(registrationUserName.getText(), "")) {
            System.out.println("Kein Benutzername!");
            regsuccsessfulllabel.setText("Kein Benutzername!");
        }
        else if (Objects.equals(registrationUserPassword.getText(), "")) {
            System.out.println("Kein Passwort!");
            regsuccsessfulllabel.setText("Kein Passwort!");
        }
        else if (Objects.equals(registrationUserQuestion.getText(), "")) {
                System.out.println("Keine Sicherheitsantwort!");
                regsuccsessfulllabel.setText("Keine Sicherheitsantwort!");
        } else {
            JavaPostgres.writeToDatabaseUser(registrationName.getText(), registrationUserName.getText(), registrationUserPassword.getText(), registrationUserQuestion.getText());
            registrationName.clear();
            registrationUserName.clear();
            registrationUserPassword.clear();
            registrationUserQuestion.clear();
            regsuccsessfulllabel.setText(null);
        }
    }

}

