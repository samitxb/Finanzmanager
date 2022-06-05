package finanzmanagerJava;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgres {

    public static void writeToDatabase(String userName, String userPassword) {

        String url = "jdbc:postgresql://localhost:5432/FinanzmanagerDb";
        String userDatabase = "postgres";
        String passwordDatabase = "root";

        String name = userName;
        String password = userPassword;

        // query
        String query = "INSERT INTO Finanzen( Username, Password) VALUES(?, ?)";

        try (Connection con = DriverManager.getConnection(url, userDatabase, passwordDatabase);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, password);
            pst.executeUpdate();
            System.out.println("Sucessfully created.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
