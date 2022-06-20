package database;

import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.Postgres.*;


/**
 * JavaPostgres-Klasse zur Anbindung an die Datenbank FinanzmanagerDb.
 * Enthält die Klasse getConnection() zum Verbindungsaufbau mit der Datenbank,
 * sowie die Klasse writetoDatabase() die für die Übergabe von Eingabedaten des
 * Nutzers zuständig ist. Umfasst die Logindaten der Nutzer
 *
 * @author Michael
 * @version 1.0.1
 *
 */



public class JavaPostgres {


    /** Datenbankverbindungsklasse getConnection()
     *  Nutzt globale Variablen als Referenz für eine Datenbank in PostgreSQL
     *  Die Datenbank verbindet sich per PostgreSQL-driver mit localhost.
     */




    // Variable databaseConnectionLink stellt Verbindungsinformationen bereit
    public Connection databaseConnectionLink;


    public Connection getConnection() {


        try {
            Class.forName("org.postgresql.Driver");
            databaseConnectionLink = DriverManager.getConnection(url, userDatabase, passwordDatabase);
            System.out.println("Successfully Connected.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseConnectionLink;
    }





    /** Datenbankeinträge speichern writeToDatabase()
     *
     *  Baut eine Verbindung zur Datenbank auf
     *  Speichert mit lokalem String query die Daten zur Nutzerregistrierung in die Tabelle userinfo
     *  Stellt die Daten als Login bereit
     *
     *
     */


    public static void writeToDatabaseUser(String fullName, String userName, String userPassword)
    {

        ResultSet resultSet;
        PreparedStatement psCheckUser;


        String queryUser = "INSERT INTO USERINFO(Fullname ,Username, Password, Passwordsalt) VALUES(?, ?, ?, ?)";


        try (Connection con = DriverManager.getConnection(url, userDatabase, passwordDatabase);
             PreparedStatement pst = con.prepareStatement(queryUser))
        {
            psCheckUser = con.prepareStatement("SELECT * FROM userinfo WHERE username=?");
            psCheckUser.setString(1, userName);
            resultSet = psCheckUser.executeQuery();


            // Checkt, ob die eingegebenen Registrierungsdaten in der Datenbank vorhanden sind
            if(resultSet.isBeforeFirst())
            {
                System.out.println("Nutzername vergeben");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Nutzername vergeben! Bitte anderen Nutzernamen wählen.");

                // Wirft Fenster mit Fehlermeldung aus
                alert.show();
            }


            else
            {
                // Generiert mit dem String salt ein Sicherheitspasswort
                String salt = PasswordEncryption.getSalt(30);

                userPassword = PasswordEncryption.generateSecurePassword(userPassword, salt);



                // Aktualisiert in der Tabelle userinfo die Einträge fullName, userName, userPassword und salt
                pst.setString(1, fullName);
                pst.setString(2, userName);
                pst.setString(3, userPassword);
                pst.setString(4, salt);
                pst.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Erfolgreich Registriert!");

                // Wirft Fenster mit Fehlermeldung aus
                alert.show();
            }

            // Wirft Fehlermeldung bei fehlgeschlagener Datenbankverbindung aus
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public static void writeToDatabaseEinnahmen( String einnahmenBeitrag, String einnahmenBezeichnung, Callback<DatePicker, DateCell> einnahmenDatum) throws SQLException
    {
            PreparedStatement ps;
            Connection con = DriverManager.getConnection(url, userDatabase, passwordDatabase);
            String SQL = ("SELECT * FROM userinfo LIMIT 1");

            try
            {

                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();


                String queryEinnahmen = "INSERT INTO EINNAHMEN(einnahmen_betrag  ,einnahmen_bezeichnung, einnahmen_datum, user_einnahmenid) VALUES(?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(queryEinnahmen);

                while (rs.next()) {
                    pst.setString(1, einnahmenBeitrag);
                    pst.setString(2, einnahmenBezeichnung);
                    pst.setDate(3, (Date) einnahmenDatum);
                    pst.setInt(4, rs.getInt(1));
                    pst.executeUpdate();
                }
            }
            catch (SQLException ex)

            {
                Logger lgr = Logger.getLogger(JavaPostgres.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }


}

