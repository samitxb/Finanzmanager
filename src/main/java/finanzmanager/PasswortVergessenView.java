package finanzmanager;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelclasses.UserLogin;
import modelclasses.UserRegistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswortVergessenView {

    @FXML
    private TextField neuesPasswort;

    @FXML
    private TextField passwortVergessenAntwort;

    @FXML
    private TextField passwortVergessenBenutzername;
    @FXML
    private Button quitPasswortView;


    ResultSet rs;


    public boolean checkSicherheitsfrage(String sicherheitsfrageEingabe) {


        JavaPostgres connectNow = new JavaPostgres();
        Connection conDb = connectNow.getConnection();


        try {

            PreparedStatement ps = conDb.prepareStatement("SELECT * FROM userinfo WHERE username=?");
            ps.setString(1, passwortVergessenBenutzername.getText());

            rs = ps.executeQuery();


            boolean hasResults = rs.next();

            if (hasResults)
            {
                do {
                    // User provided password to validate
                    String providedAntwort = passwortVergessenAntwort.getText();

                    // Encrypted and Base64 encoded password read from database
                    String secureAntwort = rs.getString("sicherheitsantwort");

                    // Salt value stored in database
                    String sicherheitsSalt = rs.getString("sicherheitsantwort_salt");


                    boolean sicherheitsfrageMatch = PasswordEncryption.verifyUserPassword(providedAntwort, secureAntwort, sicherheitsSalt);

                    if (sicherheitsfrageMatch)
                    {
                        String securePasswordSalt = PasswordEncryption.getSalt(30);


                        PreparedStatement psNewPassword = conDb.prepareStatement("Update userinfo set password=?, passwordsalt=? WHERE userid=?");
                        psNewPassword.setInt(3, rs.getInt(1));


                        String securePassword = PasswordEncryption.generateSecurePassword(sicherheitsfrageEingabe, securePasswordSalt);

                        psNewPassword.setString(1, securePassword);
                        psNewPassword.setString(2, securePasswordSalt);
                        psNewPassword.executeUpdate();

                        System.out.println("Test Pwd  " + sicherheitsfrageEingabe + "Test Salt  " + securePasswordSalt);


                    }

                } while (rs.next());

            } else {
                System.out.println("Falsche Eingabe");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    @FXML
    void quitPasswortVergessen(ActionEvent event) {
        Stage stage = (Stage) quitPasswortView.getScene().getWindow();
        stage.close();
    }

    @FXML
    void okBtnPressed(ActionEvent event)
    {
        boolean check;

        check = checkSicherheitsfrage(neuesPasswort.getText());

        if (check){
            neuesPasswort.isEditable();
        }

    }

}