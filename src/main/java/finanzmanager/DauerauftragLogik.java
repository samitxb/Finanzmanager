package finanzmanager;

import database.JavaPostgres;
import modelclasses.UserLogin;
import org.joda.time.Days;

import java.sql.*;

import java.time.*;
import java.time.temporal.ChronoUnit;

//import java.util.Date;

//import java.time.temporal.ChronoUnit;

public class DauerauftragLogik {

    public static void main(String[] args) throws SQLException {

        int id = UserLogin.id;

        //==========================================Datenbank Connection==========================================
        JavaPostgres javaPostgres = new JavaPostgres();
        Connection connection = javaPostgres.getConnection();

        try {

            //==========================================Datenbank abfrage ==========================================

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM dauerauftrag WHERE user_dauerauftragid=?");
            statement.setInt(1, 1);
            ResultSet rs = statement.executeQuery();


            while(rs.next())
            {
                int dauerauftragid = rs.getInt("dauerauftragid");


                String zeitraum = rs.getString("dauerauftrag_zeitraum");

                Date letztesDatumAbbuchung = rs.getDate("dauerauftrag_datumabbuchung");

                boolean einnahme_ausgabe = rs.getBoolean("dauerauftrag_ausgabe_einnahme");                     //1 bedeutet Einnahme, 0 bedeutet Ausgabe

                System.out.println("TEST: " + zeitraum+ letztesDatumAbbuchung+  einnahme_ausgabe);

                Date heute = Date.valueOf(LocalDate.now());

                System.out.println("TEST: " + zeitraum+ letztesDatumAbbuchung+ einnahme_ausgabe+ heute);

                //==========================================Check des Zeitraumes==========================================
                long zeitraumTage = 0;
                switch (zeitraum) {
                    case "Täglich" -> {
                        zeitraumTage = 1;
                    }
                    case "Wöchentlich" -> {
                        zeitraumTage = 7;
                    }
                    case "Monatlich" -> {
                        zeitraumTage = 30;
                    }
                    case "Jährlich" -> {
                        zeitraumTage = 365;
                    }
                }

                //========================================== Eigentliche Logik==========================================
                //==========================================Logik EInnahme==========================================


                System.out.println("TEST: " + dauerauftragid + " " + zeitraum +" " + letztesDatumAbbuchung+ " " + einnahme_ausgabe + " " + zeitraumTage);

                letztesDatumAbbuchung = Date.valueOf(letztesDatumAbbuchung.toLocalDate().plusDays(zeitraumTage));

                System.out.println("Test Tage " + letztesDatumAbbuchung);

                PreparedStatement pst = connection.prepareStatement("UPDATE Dauerauftrag SET dauerauftrag_datumabbuchung = ? WHERE user_dauerauftragid = ?");
                pst.setDate(1, letztesDatumAbbuchung);
                pst.setInt(2, 1);
                pst.executeUpdate();


            }







        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


/**
 *              writeToDatebaseEinnahmen(betrag, bezeichnung, )
 *
 *
 *
 *
 *
 *
 *
 *
 */