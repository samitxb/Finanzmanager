package finanzmanagerJava;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgres {

    public static void writeToDatabase(String fullName, String userName, String userPassword) {

        String url = "jdbc:postgresql://localhost:5432/FinanzmanagerDb";
        String userDatabase = "postgres";
        String passwordDatabase = "root";

        // query
        String query = "INSERT INTO USERINFO(Fullname ,Username, Password) VALUES(?, ?, md5(?))";

        try (Connection con = DriverManager.getConnection(url, userDatabase, passwordDatabase);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, fullName);
            pst.setString(2, userName);
            pst.setString(3, userPassword);
            pst.executeUpdate();
            System.out.println("Sucessfully created.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void readUserCredentials()
    {
        Connection connection;

        Statement statement;

        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/FinanzmanagerDb","postgres", "root");
            System.out.println("Successfully Connected.");


            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT Username, Password FROM Userinfo" );

            while ( rs.next() )
            {
                String userName = rs.getString("Username");
                String userPassword = rs.getString("Password");

                System.out.printf( "Username = %s , Password = %s", userName, userPassword);
                System.out.println();
            }

            rs.close();
            statement.close();
            connection.close();
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        System.out.println(" Data Retrieved Successfully ..");

    }
}
