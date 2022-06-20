package database;

import java.sql.*;


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

            statementDb.execute("DROP TABLE IF EXISTS dauerauftrag");
            statementDb.execute("DROP TABLE IF EXISTS ausgaben ");
            statementDb.execute("DROP TABLE IF EXISTS einnahmen ");
            statementDb.execute("DROP TABLE IF EXISTS userinfo ");




                String sqlUser = "CREATE TABLE USERINFO " +
                        "(userID             INT      GENERATED ALWAYS AS IDENTITY NOT NULL," +
                        " fullname           TEXT     NOT NULL," +
                        " username           TEXT     NOT NULL," +
                        " password           TEXT     NOT NULL," +
                        " passwordSalt       TEXT     NOT NULL," +
                        " PRIMARY KEY (userID))";


                String sqlEinnahmen = "CREATE TABLE EINNAHMEN"+
                        "(einnahmenID           INT     GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY," +
                        " user_einnahmenID      INT     NOT NULL ," +
                        " einnahmen_betrag      FLOAT," +
                        " einnahmen_bezeichnung TEXT, " +
                        " einnahmen_datum       TEXT, " +
                        " FOREIGN KEY (user_einnahmenID)" +
                        " REFERENCES userinfo(userID))";


                String sqlAusgaben = "CREATE TABLE AUSGABEN"+
                        "(ausgabenID            INT   GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY REFERENCES userinfo(userid)," +
                        " user_ausgabenID                INT   NOT NULL ," +
                        " ausgaben_betrag       FLOAT," +
                        " ausgaben_bezeichnung  VARCHAR(255), " +
                        " ausgaben_datum        DATE," +
                        " FOREIGN KEY (user_ausgabenID  )" +
                        " REFERENCES userinfo(userID))";


            String sqlDauerauftrag = "CREATE TABLE DAUERAUFTRAG "+
                    "(dauerauftragID            INT  GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY REFERENCES userinfo(userid)," +
                    " user_dauerauftragID                    INT  NOT NULL ," +
                    " dauerauftrag_betrag       FLOAT," +
                    " dauerauftrag_bezeichnung  VARCHAR(255), " +
                    " dauerauftrag_datum        DATE, " +
                    " dauerauftrag_zeitraum     DATE," +
                    " FOREIGN KEY (user_dauerauftragID)" +
                    " REFERENCES userinfo(userID))";




                statementDb.executeUpdate(sqlUser);
                statementDb.executeUpdate(sqlEinnahmen);
                statementDb.executeUpdate(sqlAusgaben);
                statementDb.executeUpdate(sqlDauerauftrag);



                ResultSet rsUserinfo = statementDb.executeQuery("SELECT * FROM USERINFO;");
                while (rsUserinfo.next())
                {
                   rsUserinfo.getInt("id");
                   rsUserinfo.getString("username");
                   rsUserinfo.getInt("password");
                }


                ResultSet rsEinnahmen = statementDb.executeQuery("SELECT userid FROM userinfo UNION Select user_einnahmenid from einnahmen;");

                while(rsEinnahmen.next())
                {
                    rsEinnahmen.getInt("id");
                    rsEinnahmen.getFloat("betrag");
                    rsEinnahmen.getString("bezeichnung");
                    rsEinnahmen.getDate("datum");
                }


                ResultSet rsAusgaben = statementDb.executeQuery("SELECT * FROM AUSGABEN;");

                while(rsAusgaben.next())
                {
                    rsAusgaben.getInt("id");
                    rsAusgaben.getFloat("betrag");
                    rsAusgaben.getString("bezeichnung");
                    rsAusgaben.getDate("datum");
                }


                ResultSet rsDauerauftrag = statementDb.executeQuery("SELECT * FROM DAUERAUFTRAG;");

                while(rsDauerauftrag.next())
                {
                    rsDauerauftrag.getInt("id");
                    rsDauerauftrag.getFloat("betrag");
                    rsDauerauftrag.getString("bezeichnung");
                    rsDauerauftrag.getDate("datum");
                    rsDauerauftrag.getDate("zeitraum");
                }


                rsUserinfo.close();
                rsEinnahmen.close();
                rsAusgaben.close();
                rsDauerauftrag.close();
                statementDb.close();
                connectionDb.close();


            } catch(Exception e){
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Tabellen wurde erstellt");

    }
}