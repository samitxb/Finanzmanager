package finanzmanagerJava;

import org.postgresql.plugin.AuthenticationPlugin;
import org.postgresql.plugin.AuthenticationRequestType;

import javax.security.auth.AuthPermission;
import javax.security.auth.spi.LoginModule;
import java.net.Authenticator;
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


    public void Authenticate(String checkName, String checkPassword)
    {

    }



    public static void readUserCredentials(String userName, String userPassword)
    {
        Connection connection;

        Statement statement;

        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/FinanzmanagerDb", "postgres", "root");
            System.out.println("Successfully Connected.");


            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Username, Password FROM Userinfo");

            while (rs.next()) {
                userName = rs.getString("Username");
                userPassword = rs.getString("Password");

                System.out.printf("Username = %s , Password = %s", userName, userPassword);
                System.out.println();
            }


            rs.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println(" Data Retrieved Successfully ..");



    }



    /**


        public static void loginWithCredentials(String checkUserName, String checkPassword) {

            String driver = "org.postgresql.Driver";
            String dbName="postgres";
            String connectionURL = "jdbc:postgresql://localhost:5432/FinanzmanagerDb";
            Connection conn = null;


            // Try to log in with no username or password
            try {
                // connection attempt should fail
                System.out.println("Trying to connect to " + connectionURL +
                        " without username or password");
                conn = DriverManager.getConnection(connectionURL);
                System.out.println(
                        "ERROR: Unexpectedly connected to database " + dbName);
                cleanUpAndShutDown(conn);
            } catch (SQLException e) {
                if (e.getSQLState().equals("08004")) {
                    System.out.println("Correct behavior: SQLException: " +
                            e.getMessage());
                } else {
                    errorPrintAndExit(e);
                }
            }

            // Log in as a user with full access
            // Create, update, and query table
            // Grant select and insert privileges to another user
            try {
                // this should succeed
                String newURL = connectionURL +
                        ";username=postgres;password=root";
                System.out.println("Trying to connect to " + newURL);
                conn = DriverManager.getConnection(newURL);
                System.out.println("Connected to database " + dbName);

                Statement s = conn.createStatement();

                s.executeUpdate(
                        "CREATE TABLE accessibletbl(textcol VARCHAR(6))");
                System.out.println("Created table accessibletbl");
                s.executeUpdate("INSERT INTO accessibletbl VALUES('hello')");

                ResultSet rs = s.executeQuery("SELECT * FROM accessibletbl");
                rs.next();
                System.out.println("Value of accessibletbl/textcol is " +
                        rs.getString(1));

                // grant insert privileges on table to user sqlsam
                s.executeUpdate(
                        "GRANT SELECT, INSERT ON accessibletbl TO sqlsam");
                System.out.println(
                        "Granted select/insert privileges to sqlsam");

                s.close();
                conn.close();
            } catch (SQLException e) {
                errorPrintAndExit(e);
            }

            // Log in as user with select and insert privileges on the table,
            //  but not delete privileges
            try {
                String newURL =
                        connectionURL + ";username=sqlsam;password=light8q9bulb";
                System.out.println("Trying to connect to " + newURL);
                conn = DriverManager.getConnection(newURL);
                System.out.println("Connected to database " + dbName);

                // look at table
                Statement s = conn.createStatement();
                ResultSet rs =
                        s.executeQuery("SELECT * FROM mary.accessibletbl");
                rs.next();
                System.out.println("Value of accessibletbl/textcol is " +
                        rs.getString(1));

                s.executeUpdate(
                        "INSERT INTO mary.accessibletbl VALUES('sam')");
                System.out.println("Inserted string into table");

                rs = s.executeQuery("SELECT * FROM mary.accessibletbl");
                while (rs.next()) {
                    System.out.println("Value of accessibletbl/textcol is " +
                            rs.getString(1));
                }
                s.close();
            } catch (SQLException e) {
                errorPrintAndExit(e);
            }

            try {
                Statement s = conn.createStatement();

                // this should fail
                s.executeUpdate("DELETE FROM mary.accessibletbl " +
                        "WHERE textcol = 'hello'");
                System.out.println("ERROR: Unexpectedly allowed to DELETE " +
                        "table mary.accessibletbl");
                cleanUpAndShutDown(conn);
            } catch (SQLException e) {
                if (e.getSQLState().equals("42500")) {
                    System.out.println("Correct behavior: SQLException: " +
                            e.getMessage());
                    try {
                        conn.close();
                    } catch (SQLException ee) {
                        errorPrintAndExit(ee);
                    }
                } else {
                    errorPrintAndExit(e);
                }
            }

            try {
                String newURL = connectionURL +
                        ";username=mary;password=little7xylamb";
                System.out.println("Trying to connect to " + newURL);
                conn = DriverManager.getConnection(newURL);
                System.out.println("Connected to database " + dbName);

                Statement s = conn.createStatement();
                s.executeUpdate("DROP TABLE accessibletbl");
                System.out.println("Removed table accessibletbl");
                s.close();
            } catch (SQLException e) {
                errorPrintAndExit(e);
            }

            try {
                cleanUpAndShutDown(conn);
            } catch (SQLException e) {
                errorPrintAndExit(e);
            }
        }


        public static void cleanUpAndShutDown (Connection conn)
                throws SQLException {

            String dbName="sqlAuthClientDB";
            String dbOwner="mary";
            String connectionURL = "jdbc:derby://localhost:1527/" + dbName;

            try {
                conn.close();
                System.out.println("Closed connection");

                // As mary, the database owner, shut down the database.
                try {
                    String newURL = connectionURL + ";user=" + dbOwner +
                            ";password=little7xylamb;shutdown=true";
                    DriverManager.getConnection(newURL);
                } catch (SQLException se) {
                    if ( !se.getSQLState().equals("08006") ) {
                        throw se;
                    }
                }
                System.out.println("Database shut down normally");
            } catch (SQLException e) {
                errorPrintAndExit(e);
            }
        }


        static void errorPrintAndExit(Throwable e) {
            if (e instanceof SQLException)
                SQLExceptionPrint((SQLException)e);
            else {
                System.out.println("A non-SQL error occurred.");
                e.printStackTrace();
            }
            System.exit(1);
        }


        static void SQLExceptionPrint(SQLException sqle) {
            while (sqle != null) {
                System.out.println("\n---SQLException Caught---\n");
                System.out.println("SQLState:   " + (sqle).getSQLState());
                System.out.println("Severity: " + (sqle).getErrorCode());
                System.out.println("Message:  " + (sqle).getMessage());
                sqle = sqle.getNextException();
            }
        }

     */



    }

