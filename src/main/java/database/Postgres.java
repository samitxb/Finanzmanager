package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Klasse Postgres ist eine Vorkonfigurationsklasse für die Datenbank FinanzmanagerDb
 *
 * @author Michael
 * @version 1.0.1
 *
 */




class Postgres {


    // Url der Datenbank FinanzmanagerDb
    static String url = "jdbc:postgresql://localhost:5432/FinanzmanagerDb";

    // Name für Admin der Datenbank
    static String userDatabase = "postgres";

    // Passwort für Zugriff der Datenbank
    static String passwordDatabase = "root";


    /**
     * Bei Ausführung der main()-Funktion wird die Tabelle userinfo angelegt

     */

    public static void main(String[] args) {


        Connection connectionDb;
        Statement statementDb;
        try {

            Class.forName("org.postgresql.Driver");
            connectionDb = DriverManager
                    .getConnection(url, userDatabase, passwordDatabase);
            System.out.println("Opened database successfully");

                statementDb = connectionDb.createStatement();

                String sql = "CREATE TABLE USERINFO " +
                        "(UserID             INT   GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY ," +
                        " Fullname           TEXT     NOT NULL," +
                        " Username           TEXT     NOT NULL," +
                        " Password           TEXT     NOT NULL," +
                        " PasswordSalt       TEXT     NOT NULL)";
                statementDb.executeUpdate(sql);


                ResultSet rs = statementDb.executeQuery("SELECT * FROM USERINFO;");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("username");
                    int age = rs.getInt("password");

                    System.out.println("ID = " + id);
                    System.out.println("Username = " + name);
                    System.out.println("Password= " + age);

                    System.out.println();
                }

                rs.close();
                statementDb.close();
                connectionDb.close();

            } catch(Exception e){
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Tabelle userinfo wurde erstellt");

    }
}