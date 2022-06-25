package finanzmanager;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelclasses.UserLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PasswortVergessenView {

    @FXML
    private TextField altesPasswort;

    @FXML
    private TextField passwortVergessenAntwort;

    @FXML
    private TextField passwortVergessenBenutzername;

    @FXML
    private TextField sicherheitsfrageAntwort;

    @FXML
    private Button quitPasswortView;




    public boolean checkSicherheitsfrage (String sicherheitsfrageEingabe)
    {
        int id = UserLogin.id;
        ResultSet rs;


        System.out.println(sicherheitsfrageEingabe);

        JavaPostgres connectNow = new JavaPostgres();
        Connection conDb = connectNow.getConnection();



        try
        {

            PreparedStatement ps = conDb.prepareStatement("SELECT * FROM userinfo WHERE userid=? AND sicherheitsanwort=? ");
            ps.setInt(1,id);
            rs = ps.executeQuery();

            boolean hasResults= rs.next();

            if(hasResults)
            {
                do
                {
                    // User provided password to validate
                    String providedAntwort = sicherheitsfrageEingabe;

                    // Encrypted and Base64 encoded password read from database
                    String secureSicherheitsfrage = rs.getString("password");

                    // Salt value stored in database
                    String sicherheitsSalt = rs.getString("passwordsalt");


                    PasswordEncryption verifySicherheitsfrage = new PasswordEncryption();

                    verifySicherheitsfrage.equals(PasswordEncryption.verifyUserPassword(providedAntwort, secureSicherheitsfrage,sicherheitsSalt));


                    boolean sicherheitsfrageMatch = PasswordEncryption.verifyUserPassword(providedAntwort, secureSicherheitsfrage, secureSicherheitsfrage);

                    if(sicherheitsfrageMatch)
                    {
                        sicherheitsfrageEingabe = rs.getString(2);
                        System.out.println("Test: " + sicherheitsfrageEingabe);

                        return true;
                    }

                }while (rs.next());

            }
            else {
                System.out.println("Falsche Eingabe");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
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
        checkSicherheitsfrage(sicherheitsfrageAntwort.getText());

    }

}