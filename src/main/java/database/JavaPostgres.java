package database;

import javafx.scene.control.Alert;
import modelclasses.UserLogin;

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
 */


public class JavaPostgres {


    /**
     * Datenbankverbindungsklasse getConnection()
     * Nutzt globale Variablen als Referenz für eine Datenbank in PostgreSQL
     * Die Datenbank verbindet sich per PostgreSQL-driver mit localhost.
     */


    // Variable databaseConnectionLink stellt Verbindungsinformationen bereit
    public static Connection databaseConnectionLink;

    /**
     * Datenbankeinträge speichern writeToDatabase()
     * <p>
     * Baut eine Verbindung zur Datenbank auf
     * Speichert mit lokalem String query die Daten zur Nutzerregistrierung in die Tabelle userinfo
     * Stellt die Daten als Login bereit
     */


    public static void writeToDatabaseUser(String fullName, String userName, String userPassword, String sicherheitsantwort) {

        ResultSet resultSet;
        PreparedStatement psCheckUser;


        String queryUser = "INSERT INTO USERINFO(Fullname ,Username, Password, Passwordsalt, Kontostand, Sicherheitsantwort, Sicherheitsantwort_salt) VALUES(?, ?, ?, ?,?, ?,?)";


        try (Connection con = DriverManager.getConnection(url, userDatabase, passwordDatabase);
             PreparedStatement pst = con.prepareStatement(queryUser)) {
            psCheckUser = con.prepareStatement("SELECT * FROM userinfo WHERE username=?");
            psCheckUser.setString(1, userName);
            resultSet = psCheckUser.executeQuery();


            // Checkt, ob die eingegebenen Registrierungsdaten in der Datenbank vorhanden sind
            if (resultSet.isBeforeFirst()) {
                System.out.println("Nutzername vergeben");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Nutzername vergeben! Bitte anderen Nutzernamen wählen.");

                // Wirft Fenster mit Fehlermeldung aus
                alert.show();
            } else {
                // Generiert mit dem String salt ein Sicherheitspasswort
                String salt = PasswordEncryption.getSalt(30);
                String sicherheitsantwortSalt = PasswordEncryption.getSalt(30);

                userPassword = PasswordEncryption.generateSecurePassword(userPassword, salt);
                sicherheitsantwort = PasswordEncryption.generateSecurePassword(sicherheitsantwort, sicherheitsantwortSalt);

                float kontostand = 0;

                // Aktualisiert in der Tabelle userinfo die Einträge fullName, userName, userPassword und salt
                pst.setString(1, fullName);
                pst.setString(2, userName);
                pst.setString(3, userPassword);
                pst.setString(4, salt);
                pst.setFloat(5, kontostand);
                pst.setString(6, sicherheitsantwort);
                pst.setString(7, sicherheitsantwortSalt);
                pst.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Erfolgreich Registriert!");

                // Wirft Fenster mit Fehlermeldung aus
                alert.show();
            }
            resultSet.close();

            // Wirft Fehlermeldung bei fehlgeschlagener Datenbankverbindung aus
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void writeToDatabaseEinnahmen(Float einnahmenBetrag, String einnahmenBezeichnung, Date einnahmenDatum) {

        int id = UserLogin.id;


        try {

            PreparedStatement ps;
            Connection con = DriverManager.getConnection(url, userDatabase, passwordDatabase);

            ps = con.prepareStatement("SELECT userid FROM Userinfo WHERE userid = ? ", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();


            String queryEinnahmen = "INSERT INTO EINNAHMEN(einnahmen_betrag  ,einnahmen_bezeichnung, einnahmen_datum, user_einnahmenid) VALUES(?, ?, ?, ?)";
            PreparedStatement pstEinnahmen = con.prepareStatement(queryEinnahmen);

            boolean hasResults = rs.next();


            if (hasResults) {
                do {
                    System.out.println("USERID:  " + id);
                    pstEinnahmen.setFloat(1, einnahmenBetrag);
                    pstEinnahmen.setString(2, einnahmenBezeichnung);
                    pstEinnahmen.setDate(3, einnahmenDatum);
                    pstEinnahmen.setInt(4, id);
                    pstEinnahmen.executeUpdate();
                    pstEinnahmen.close();
                } while (rs.next());
                pstEinnahmen.close();
                rs.close();
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(JavaPostgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void writeToDatabaseAusgaben(Float ausgabenBetrag, String ausgabenBezeichnung, Date ausgabenDatum) throws SQLException {
        int id = UserLogin.id;

        PreparedStatement ps;
        Connection con = DriverManager.getConnection(url, userDatabase, passwordDatabase);
        String SQL = ("SELECT * FROM userinfo LIMIT 1");

        try {

            ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();


            String queryAusgaben = "INSERT INTO AUSGABEN(ausgaben_betrag  ,ausgaben_bezeichnung, ausgaben_datum, user_ausgabenid) VALUES(?, ?, ?, ?)";
            PreparedStatement pstAusgaben = con.prepareStatement(queryAusgaben);

            while (rs.next()) {
                pstAusgaben.setFloat(1, ausgabenBetrag);
                pstAusgaben.setString(2, ausgabenBezeichnung);
                pstAusgaben.setDate(3, ausgabenDatum);
                pstAusgaben.setInt(4, id);
                pstAusgaben.executeUpdate();
            }
            pstAusgaben.close();
            rs.close();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(JavaPostgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void writeToDatabaseDauerauftrag(Float dauerauftragBetrag, String dauerauftragBezeichnung, Date dauerauftragDatum, String dauerauftragZeitraum) throws SQLException {
        PreparedStatement ps;
        Connection con = DriverManager.getConnection(url, userDatabase, passwordDatabase);
        String SQL = ("SELECT * FROM userinfo LIMIT 1");

        int id = UserLogin.id;

        try {

            ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();


            String queryDauerauftrag = "INSERT INTO DAUERAUFTRAG(dauerauftrag_betrag  ,dauerauftrag_bezeichnung, dauerauftrag_datum, user_dauerauftragid, dauerauftrag_zeitraum) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstDauerauftrag = con.prepareStatement(queryDauerauftrag);

            while (rs.next()) {
                pstDauerauftrag.setFloat(1, dauerauftragBetrag);
                pstDauerauftrag.setString(2, dauerauftragBezeichnung);
                pstDauerauftrag.setDate(3, dauerauftragDatum);
                pstDauerauftrag.setInt(4, id);
                pstDauerauftrag.setString(5, dauerauftragZeitraum);
                pstDauerauftrag.executeUpdate();
            }
            pstDauerauftrag.close();
            rs.close();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(JavaPostgres.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

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


}
