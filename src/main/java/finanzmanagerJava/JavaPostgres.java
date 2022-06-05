package finanzmanagerJava;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgres {

    public static void writeToDatabase(String fullName, String userName, String userPassword) {

        String url = "jdbc:postgresql://localhost:5432/FinanzmanagerDb";
        String userDatabase = "postgres";
        String passwordDatabase = "root";

        String nameOfPerson = fullName;
        String nameOfUser = userName;
        String passwordOfUser = userPassword;

        // query
        String query = "INSERT INTO USERINFO(Fullname ,Username, Password) VALUES(?, ?, md5(?))";

        try (Connection con = DriverManager.getConnection(url, userDatabase, passwordDatabase);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, nameOfPerson);
            pst.setString(2, nameOfUser);
            pst.setString(3, passwordOfUser);
            pst.executeUpdate();
            System.out.println("Sucessfully created.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
