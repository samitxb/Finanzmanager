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

    public void test() throws SQLException {

        int id = UserLogin.id;


        //==========================================Datenbank Connection==========================================
        JavaPostgres javaPostgres = new JavaPostgres();
        Connection connection = javaPostgres.getConnection();

        try {

            //==========================================Datenbank abfrage ==========================================

            PreparedStatement statement = connection.prepareStatement("SELECT dauerauftragid, dauerauftrag_zeitraum, dauerauftrag_datumabbuchung, dauerauftrag_ausgabe_einnahme FROM dauerauftrag WHERE user_dauerauftragid=?");
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();

            while (res.next()){
                int dauerauftragid = res.getInt("dauerauftragid");
                String zeitraum = res.getString("dauerauftrag_zeitraum");

                Date letztesDatumAbbuchung = res.getDate("dauerauftrag_datumabbuchung");
                Instant i_letztesDatumAbbuchung = letztesDatumAbbuchung.toInstant();

                boolean einnahme_ausgabe = res.getBoolean("dauerauftrag_ausgabe_einnahme");                     //1 bedeutet Einnahme, 0 bedeutet Ausgabe

                Date heute = Date.valueOf(LocalDate.now());
                Instant i_heute = heute.toInstant();

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
                if (einnahme_ausgabe){
                    long tageDazwischen = ChronoUnit.DAYS.between(i_letztesDatumAbbuchung, i_heute);

                    //int test = tageDazwischen / zeitraumTage;

                }





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