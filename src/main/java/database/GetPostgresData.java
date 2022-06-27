package database;

import modelclasses.UserLogin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.JavaPostgres.databaseConnectionLink;


public class GetPostgresData {


    public static void getAusgabenDatabase()
    {

        int ausgabenUserId = UserLogin.id;


        List<String> getAusgabenListBetrag = new ArrayList<>();
        List<String> getAusgabenListBezeichnung = new ArrayList<>();
        List<String> getAusgabenListDatum = new ArrayList<>();

        JavaPostgres javaPostgres = new JavaPostgres();
        javaPostgres.getConnection();



        try {
            PreparedStatement pstAusgaben;
            Connection con = databaseConnectionLink;
            Statement st = con.createStatement();

            st.setFetchSize(50);

            pstAusgaben = con.prepareStatement("SELECT * FROM ausgaben WHERE user_ausgabenid=?");
            pstAusgaben.setInt(1, ausgabenUserId);

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


    public static void getEinnahmenDatabase()
    {
        int einnahmenUserId = UserLogin.id;


        List<String> getEinnahmenListBetrag = new ArrayList<>();
        List<String> getEinnahmenListBezeichnung = new ArrayList<>();
        List<String> getEinnahmenListDatum = new ArrayList<>();

        JavaPostgres javaPostgres = new JavaPostgres();
        javaPostgres.getConnection();


        try {
            PreparedStatement pstEinnahmen;
            Connection con = databaseConnectionLink;
            Statement st = con.createStatement();

            st.setFetchSize(50);

            pstEinnahmen = con.prepareStatement("SELECT * FROM einnahmen WHERE user_einnahmenid=?");
            pstEinnahmen.setInt(1, einnahmenUserId);

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


    public static void getDauerauftragDatabase()
    {
        int dauerauftragUserId = UserLogin.id;


        List<String> getDauerauftragListBetrag = new ArrayList<>();
        List<String> getDauerauftragListBezeichnung = new ArrayList<>();
        List<String> getDauerauftragListDatum = new ArrayList<>();
        List<String> getDauerauftragListZeitraum = new ArrayList<>();

        JavaPostgres javaPostgres = new JavaPostgres();
        javaPostgres.getConnection();



        try {
            PreparedStatement pstDauerauftrag;
            Connection con = databaseConnectionLink;
            Statement st = con.createStatement();

            st.setFetchSize(50);

            pstDauerauftrag = con.prepareStatement("SELECT * FROM dauerauftrag WHERE user_dauerauftragid=?");
            pstDauerauftrag.setInt(1, dauerauftragUserId);

            ResultSet rs = pstDauerauftrag.executeQuery();

            while (rs.next()) {
                getDauerauftragListBetrag.add(rs.getString("dauerauftrag_betrag"));
                getDauerauftragListBezeichnung.add(rs.getString("dauerauftrag_bezeichnung"));
                getDauerauftragListDatum.add(rs.getString("dauerauftrag_datum"));
                getDauerauftragListZeitraum.add(rs.getString("dauerauftrag_zeitraum"));
            }
            rs.close();
            System.out.print("Data Dauerauftrag:\n " + getDauerauftragListBetrag + " \n" + getDauerauftragListBezeichnung + "\n" + getDauerauftragListDatum +  "\n" + "\n\n");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(GetPostgresData.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }




}
