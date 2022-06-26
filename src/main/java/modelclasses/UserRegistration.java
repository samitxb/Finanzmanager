package modelclasses;

import database.JavaPostgres;
import finanzmanager.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Objects;

public class UserRegistration {


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

