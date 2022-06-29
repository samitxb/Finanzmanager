package database;

import modelclasses.UserLogin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.JavaPostgres.databaseConnectionLink;

/**
 * Klasse GetPostgresData umfasst alle benötigte Funktionen, um die Einträge aus den Datenbanktabellen zu holen
 * und in der GUI anzuzeigen
 *
 * @author Michael
 * @version 1.0.1
 */


public class GetPostgresData {

    static int id = UserLogin.id;
    static JavaPostgres javaPostgres = new JavaPostgres();


    /**
     * Funktion getAusgabenDatabase() nimmt die Daten aus der Datenbank
     * für den jeweiligen User aus der Tabelle Ausgaben
     * Fügt die Daten in die lokale ArrayList und wird in die Tableview übergeben
     */

    public static void getAusgabenDatabase() {
        javaPostgres.getConnection();


        List<String> getAusgabenListBetrag = new ArrayList<>();
        List<String> getAusgabenListBezeichnung = new ArrayList<>();
        List<String> getAusgabenListDatum = new ArrayList<>();


        try {
            PreparedStatement pstAusgaben;
            Connection con = databaseConnectionLink;
            Statement st = con.createStatement();

            st.setFetchSize(50);

            pstAusgaben = con.prepareStatement("SELECT * FROM ausgaben WHERE user_ausgabenid=?");
            pstAusgaben.setInt(1, id);

            ResultSet rs = pstAusgaben.executeQuery();
            while (rs.next()) {

                getAusgabenListBetrag.add(rs.getString("ausgaben_betrag"));
                getAusgabenListBezeichnung.add(rs.getString("ausgaben_bezeichnung"));
                getAusgabenListDatum.add(rs.getString("ausgaben_datum"));
            }

            System.out.print("Data Ausgaben:\n " + getAusgabenListBetrag + " \n" + getAusgabenListBezeichnung + "\n" + getAusgabenListDatum + "\n\n");

            rs.close();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(GetPostgresData.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    /**
     * Funktion getEinnahmenDatabase() nimmt die Daten aus der Datenbank für den
     * jeweiligen User aus der Tabelle Einnahmen
     * Fügt die Daten in die lokale ArrayList und wird in die Tableview übergeben
     */


    public static void getEinnahmenDatabase() {


        List<String> getEinnahmenListBetrag = new ArrayList<>();
        List<String> getEinnahmenListBezeichnung = new ArrayList<>();
        List<String> getEinnahmenListDatum = new ArrayList<>();


        try {
            PreparedStatement pstEinnahmen;
            Connection con = databaseConnectionLink;
            Statement st = con.createStatement();

            st.setFetchSize(50);

            pstEinnahmen = con.prepareStatement("SELECT * FROM einnahmen WHERE user_einnahmenid=?");
            pstEinnahmen.setInt(1, id);

            ResultSet rs = pstEinnahmen.executeQuery();
            while (rs.next()) {

                getEinnahmenListBetrag.add(rs.getString("einnahmen_betrag"));
                getEinnahmenListBezeichnung.add(rs.getString("einnahmen_bezeichnung"));
                getEinnahmenListDatum.add(rs.getString("einnahmen_datum"));
            }
            System.out.print("Data Einnahmen:\n" + getEinnahmenListBetrag + " \n" + getEinnahmenListBezeichnung + "\n  " + getEinnahmenListDatum + "\n\n");
            rs.close();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(GetPostgresData.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }


    }


    /**
     * Funktion getDauerauftragDatabase() nimmt die Daten aus der Datenbank
     * für den jeweiligen User aus der Tabelle Dauerauftrag
     * Fügt die Daten in die lokale ArrayList und wird in die Tableview übergeben
     */


    public static void getDauerauftragDatabase() {


        List<String> getDauerauftragListBetrag = new ArrayList<>();
        List<String> getDauerauftragListBezeichnung = new ArrayList<>();
        List<String> getDauerauftragListDatum = new ArrayList<>();
        List<String> getDauerauftragListZeitraum = new ArrayList<>();


        try {
            PreparedStatement pstDauerauftrag;
            Connection con = databaseConnectionLink;
            Statement st = con.createStatement();

            st.setFetchSize(50);

            pstDauerauftrag = con.prepareStatement("SELECT * FROM dauerauftrag WHERE user_dauerauftragid=?");
            pstDauerauftrag.setInt(1, id);

            ResultSet rs = pstDauerauftrag.executeQuery();

            while (rs.next()) {
                getDauerauftragListBetrag.add(rs.getString("dauerauftrag_betrag"));
                getDauerauftragListBezeichnung.add(rs.getString("dauerauftrag_bezeichnung"));
                getDauerauftragListDatum.add(rs.getString("dauerauftrag_datum"));
                getDauerauftragListZeitraum.add(rs.getString("dauerauftrag_zeitraum"));
            }
            rs.close();
            System.out.print("Data Dauerauftrag:\n " + getDauerauftragListBetrag + " \n" + getDauerauftragListBezeichnung + "\n" + getDauerauftragListDatum + "\n" + "\n\n");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(GetPostgresData.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


}
