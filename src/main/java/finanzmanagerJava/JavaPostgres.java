package finanzmanagerJava;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.postgresql.plugin.AuthenticationPlugin;
import org.postgresql.plugin.AuthenticationRequestType;

import javax.security.auth.AuthPermission;
import javax.security.auth.spi.LoginModule;
import java.net.Authenticator;
import finanzmanagerJava.Controller;
import modelClasses.UserInfo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgres {

    public Connection databaseConnectionLink;

    public Connection getConnection() {

        String databaseName = "FinanzmanagerDb";
        String url = "jdbc:postgresql://localhost:5432/" + databaseName;
        String databaseUserName = "postgres";
        String databasePassword = "root";

        try {
            Class.forName("org.postgresql.Driver");
            databaseConnectionLink = DriverManager.getConnection(url, databaseUserName, databasePassword);
            System.out.println("Successfully Connected.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseConnectionLink;
    }


    public static void writeToDatabase(String fullName, String userName, String userPassword) {

        String url = "jdbc:postgresql://localhost:5432/FinanzmanagerDb";
        String userDatabase = "postgres";
        String passwordDatabase = "root";
        ResultSet resultSet;
        PreparedStatement psCheckUser;

        // query
        String query = "INSERT INTO USERINFO(Fullname ,Username, Password) VALUES(?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, userDatabase, passwordDatabase);
             PreparedStatement pst = con.prepareStatement(query))
        {
            psCheckUser = con.prepareStatement("SELECT * FROM userinfo WHERE username=?");
            psCheckUser.setString(1, userName);
            resultSet = psCheckUser.executeQuery();

            if(resultSet.isBeforeFirst())
            {
                System.out.println("Nutzername vergeben");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Nutzername vergeben! Bitte anderen Nutzernamen wählen.");
                alert.show();
            }
            else
            {
                pst.setString(1, fullName);
                pst.setString(2, userName);
                pst.setString(3, userPassword);
                pst.executeUpdate();
                System.out.println("Sucessfully created.");
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


}
