package modelclasses;

import database.JavaPostgres;
import finanzmanager.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.Objects;

public class UserRegistration {

   /*
    @FXML
    protected TextField registrationName;

    @FXML
    protected TextField registrationUserName;

    @FXML
    protected TextField registrationUserPassword; */

    public static void setRegistrationData(TextField registrationName, TextField registrationUserName, TextField registrationUserPassword){

        System.out.println(registrationUserName.getText());
        System.out.println(registrationUserPassword.getText());

        if (Objects.equals(registrationName.getText(), ""))
        {
            //regsuccsessfulllabel.setText("Kein Name!");
        }
        else if(Objects.equals(registrationUserName.getText(), "")) {
            //regsuccsessfulllabel.setText("Kein Benutzername!");
        }
        else if (Objects.equals(registrationUserPassword.getText(), "")) {
            //regsuccsessfulllabel.setText("Kein Passwort!");
        } else {
            JavaPostgres.writeToDatabaseUser(registrationName.getText(), registrationUserName.getText(), registrationUserPassword.getText());
            registrationName.clear();
            registrationUserName.clear();
            registrationUserPassword.clear();
        }
    }

}

