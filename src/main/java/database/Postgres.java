/*
 * Klasse dient zur Erstellung der Datenbank
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


/**
 * Klasse Postgres ist eine Vorkonfigurationsklasse für die Datenbank FinanzmanagerDb
 * Bei Ausführung wird die Datenbank vorkonfiguriert
 *
 * @author Michael
 * @version 1.0.1
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

            statementDb.execute("DROP TABLE IF EXISTS dauerauftrag");
            statementDb.execute("DROP TABLE IF EXISTS ausgaben ");
            statementDb.execute("DROP TABLE IF EXISTS einnahmen ");
            statementDb.execute("DROP TABLE IF EXISTS userinfo ");


            String sqlUser = "CREATE TABLE USERINFO " +
                    "(userID                  INT      GENERATED ALWAYS AS IDENTITY NOT NULL," +
                    " fullname                TEXT     NOT NULL," +
                    " username                TEXT     NOT NULL," +
                    " password                TEXT     NOT NULL," +
                    " passwordSalt            TEXT     NOT NULL," +
                    " kontostand              FLOAT     ," +
                    " sicherheitsantwort      TEXT      NOT NULL, " +
                    " sicherheitsantwort_salt TEXT      NOT NULL," +
                    " PRIMARY KEY (userID))";


            String sqlEinnahmen = "CREATE TABLE EINNAHMEN" +
                    "(einnahmenID           INT     GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY," +
                    " user_einnahmenID      INT     NOT NULL ," +
                    " einnahmen_betrag      FLOAT," +
                    " einnahmen_bezeichnung TEXT, " +
                    " einnahmen_datum       DATE, " +
                    " FOREIGN KEY (user_einnahmenID)" +
                    " REFERENCES userinfo(userID))";


            String sqlAusgaben = "CREATE TABLE AUSGABEN" +
                    "(ausgabenID            INT   GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY," +
                    " user_ausgabenID       INT   NOT NULL ," +
                    " ausgaben_betrag       FLOAT," +
                    " ausgaben_bezeichnung  TEXT, " +
                    " ausgaben_datum        DATE," +
                    " FOREIGN KEY (user_ausgabenID  )" +
                    " REFERENCES userinfo(userID))";


            String sqlDauerauftrag = "CREATE TABLE DAUERAUFTRAG " +
                    "(dauerauftragID            INT  GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY," +
                    " user_dauerauftragID       INT  NOT NULL ," +
                    " dauerauftrag_betrag       FLOAT," +
                    " dauerauftrag_bezeichnung  TEXT, " +
                    " dauerauftrag_datum        DATE, " +
                    " dauerauftrag_zeitraum     TEXT," +
                    " dauerauftrag_datumabbuchung DATE, " +
                    "dauerauftrag_ausgabe_einnahme BOOLEAN, " +
                    " FOREIGN KEY (user_dauerauftragID)" +
                    " REFERENCES userinfo(userID))";

            statementDb.executeUpdate(sqlUser);
            statementDb.executeUpdate(sqlEinnahmen);
            statementDb.executeUpdate(sqlAusgaben);
            statementDb.executeUpdate(sqlDauerauftrag);

            statementDb.close();
            connectionDb.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Tabellen wurde erstellt");
    }

}
