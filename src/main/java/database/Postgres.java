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
 * @author Michael Irlmeier
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


            /**
             * SQL-Befehle zur Erstellung der relationalen Datenbanktabellen mit Primär- und Fremdschlüsseln
             */

            String sqlUser = "CREATE TABLE USERINFO " +
                    "(userID                  INT      GENERATED ALWAYS AS IDENTITY NOT NULL," +
                    " fullname                VARCHAR(100)     NOT NULL," +
                    " username                VARCHAR(100)     NOT NULL," +
                    " password                VARCHAR(100)     NOT NULL," +
                    " passwordSalt            VARCHAR(100)     NOT NULL," +
                    " kontostand              NUMERIC     ," +
                    " sicherheitsantwort      VARCHAR(100)      NOT NULL, " +
                    " sicherheitsantwort_salt VARCHAR(100)      NOT NULL," +
                    " PRIMARY KEY (userID))";


            String sqlEinnahmen = "CREATE TABLE EINNAHMEN" +
                    "(einnahmenID           INT     GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY," +
                    " user_einnahmenID      INT     NOT NULL ," +
                    " einnahmen_betrag      NUMERIC," +
                    " einnahmen_bezeichnung VARCHAR(100), " +
                    " einnahmen_datum       DATE, " +
                    " FOREIGN KEY (user_einnahmenID)" +
                    " REFERENCES userinfo(userID))";


            String sqlAusgaben = "CREATE TABLE AUSGABEN" +
                    "(ausgabenID            INT   GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY," +
                    " user_ausgabenID       INT   NOT NULL ," +
                    " ausgaben_betrag       NUMERIC," +
                    " ausgaben_bezeichnung  VARCHAR(100), " +
                    " ausgaben_datum        DATE," +
                    " FOREIGN KEY (user_ausgabenID  )" +
                    " REFERENCES userinfo(userID))";


            String sqlDauerauftrag = "CREATE TABLE DAUERAUFTRAG " +
                    "(dauerauftragID            INT  GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY," +
                    " user_dauerauftragID       INT  NOT NULL ," +
                    " dauerauftrag_betrag       NUMERIC," +
                    " dauerauftrag_bezeichnung  VARCHAR(100), " +
                    " dauerauftrag_datum        DATE, " +
                    " dauerauftrag_zeitraum     VARCHAR(100)," +
                    " dauerauftrag_datumabbuchung DATE, " +
                    " dauerauftrag_ausgabe_einnahme BOOLEAN, " +
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
