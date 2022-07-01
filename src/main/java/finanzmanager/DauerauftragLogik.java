package finanzmanager;

import database.JavaPostgres;
import modelclasses.UserLogin;

import java.sql.*;
import java.time.*;


public class DauerauftragLogik {

    public static void dauerauftragKontrolle() throws SQLException {

        int id = UserLogin.id;

        //==========================================Datenbank Connection==========================================
        JavaPostgres javaPostgres = new JavaPostgres();
        Connection connection = javaPostgres.getConnection();

        try {
            //==========================================Datenbank abfrage ==========================================
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM dauerauftrag WHERE user_dauerauftragid=?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                //int dauerauftragid = rs.getInt("dauerauftragid");

                String bezeichnung = rs.getString("dauerauftrag_bezeichnung");

                float betrag = rs.getFloat("dauerauftrag_betrag");

                String zeitraum = rs.getString("dauerauftrag_zeitraum");

                Date letztesDatumBuchung = rs.getDate("dauerauftrag_datumabbuchung");

                boolean einnahme_ausgabe = rs.getBoolean("dauerauftrag_ausgabe_einnahme");                     //1 bedeutet Einnahme, 0 bedeutet Ausgabe

                Date heute = Date.valueOf(LocalDate.now());

                //==========================================Check des Zeitraumes==========================================
                long zeitraumTage = 0;
                boolean monatlich = false;
                boolean jährlich = false;

                switch (zeitraum) {
                    case "Täglich" -> {
                        zeitraumTage = 1;
                    }
                    case "Wöchentlich" -> {
                        zeitraumTage = 7;
                    }
                    case "Monatlich" -> {
                        monatlich = true;
                    }
                    case "Jährlich" -> {
                        jährlich = true;
                    }
                }

                //========================================== Eigentliche Logik==========================================
                //==========================================Logik Einnahme==========================================

                if (einnahme_ausgabe) {

                    PreparedStatement pst = connection.prepareStatement("UPDATE Dauerauftrag SET dauerauftrag_datumabbuchung = ? WHERE user_dauerauftragid = ?");

                    Date zwischenDate = letztesDatumBuchung;

                    //===========================Monatlich zum selben Tag=====================================
                    if (monatlich) {
                        while (zwischenDate.before(heute)) {

                            zwischenDate = Date.valueOf(zwischenDate.toLocalDate().plusMonths(1));

                            if (zwischenDate.before(heute)) {
                                pst.setDate(1, zwischenDate);
                                pst.setInt(2, id);
                                pst.executeUpdate();

                                JavaPostgres.writeToDatabaseEinnahmen(betrag, bezeichnung, zwischenDate);
                            }
                        }

                    } else if (jährlich) {
                        while (zwischenDate.before(heute)) {

                            zwischenDate = Date.valueOf(zwischenDate.toLocalDate().plusYears(1));

                            if (zwischenDate.before(heute)) {
                                pst.setDate(1, zwischenDate);
                                pst.setInt(2, id);
                                pst.executeUpdate();

                                JavaPostgres.writeToDatabaseEinnahmen(betrag, bezeichnung, zwischenDate);
                            }
                        }

                    } else {
                        while (zwischenDate.before(heute)) {

                            zwischenDate = Date.valueOf(zwischenDate.toLocalDate().plusDays(zeitraumTage));

                            if (zwischenDate.before(heute)) {
                                pst.setDate(1, zwischenDate);
                                pst.setInt(2, id);
                                pst.executeUpdate();

                                JavaPostgres.writeToDatabaseEinnahmen(betrag, bezeichnung, zwischenDate);
                            }
                        }
                    }
                }

                //==========================================Logik Ausgabe==========================================

                else {
                    PreparedStatement pst = connection.prepareStatement("UPDATE Dauerauftrag SET dauerauftrag_datumabbuchung = ? WHERE user_dauerauftragid = ?");

                    Date zwischenDate = letztesDatumBuchung;

                    //===========================Monatlich zum selben Tag=====================================
                    if (monatlich) {
                        while (zwischenDate.before(heute)) {

                            zwischenDate = Date.valueOf(zwischenDate.toLocalDate().plusMonths(1));

                            if (zwischenDate.before(heute)) {
                                pst.setDate(1, zwischenDate);
                                pst.setInt(2, id);
                                pst.executeUpdate();

                                JavaPostgres.writeToDatabaseAusgaben(betrag, bezeichnung, zwischenDate);
                            }
                        }

                    }
                    //===========================Jährlich zum selben Tag=====================================

                    else if (jährlich) {
                        while (zwischenDate.before(heute)) {

                            zwischenDate = Date.valueOf(zwischenDate.toLocalDate().plusYears(1));

                            if (zwischenDate.before(heute)) {
                                pst.setDate(1, zwischenDate);
                                pst.setInt(2, id);
                                pst.executeUpdate();

                                JavaPostgres.writeToDatabaseAusgaben(betrag, bezeichnung, zwischenDate);
                            }
                        }
                    } else {
                        while (zwischenDate.before(heute)) {

                            zwischenDate = Date.valueOf(zwischenDate.toLocalDate().plusDays(zeitraumTage));

                            if (zwischenDate.before(heute)) {
                                pst.setDate(1, zwischenDate);
                                pst.setInt(2, id);
                                pst.executeUpdate();

                                JavaPostgres.writeToDatabaseAusgaben(betrag, bezeichnung, zwischenDate);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
