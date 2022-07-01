/*
Diese Klasse wurde hauptsächlich erstellt, um die Arbeitsaufteilung etwas ansehnlicher für den Prüfer zu machen. Dieser
Code hätte auch in eine andere Klasse eingefügt werden können.
 */

package finanzmanager;

import database.JavaPostgres;
import modelclasses.UserLogin;

import java.sql.*;
import java.time.*;

/**
 * Datum: 01.07.2022
 *
 * @author Sami Taieb
 * @version 1.6.4
 */
public class DauerauftragLogik {

    public static void dauerauftragKontrolle() throws SQLException {

        int id = UserLogin.id;

        //Stellt Verbindung zur Datenbank her mithilfe der Klasse JavaPostgres
        JavaPostgres javaPostgres = new JavaPostgres();
        Connection connection = javaPostgres.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM dauerauftrag WHERE user_dauerauftragid=?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String bezeichnung = rs.getString("dauerauftrag_bezeichnung");
                float betrag = rs.getFloat("dauerauftrag_betrag");
                String zeitraum = rs.getString("dauerauftrag_zeitraum");
                Date letztesDatumBuchung = rs.getDate("dauerauftrag_datumabbuchung");
                Date heute = Date.valueOf(LocalDate.now());
                /*
                Um eine Einnahme von eine Ausgabe zu unterscheiden, wird ein boolscher Ausdruck verwendet, wobei
                TRUE für eine Einnahme und FALSE für eine Ausgabe steht. Dies wird später noch berücksichtigt
                 */
                boolean einnahmeAusgabe = rs.getBoolean("dauerauftrag_ausgabe_einnahme");

                /*
                Da Monate und Jahre nicht immer den selben Abstand haben, wird hier schon vorher abgefragt um welches
                Intervall es sich hierbei handelt, um später die Daten richtig zu verarbeiten
                 */
                long zeitraumTage = 0;
                boolean monatlich = false;
                boolean jaehrlich = false;

                switch (zeitraum) {
                    case "Täglich" -> zeitraumTage = 1;
                    case "Wöchentlich" -> zeitraumTage = 7;
                    case "Monatlich" -> monatlich = true;
                    case "Jährlich" -> jaehrlich = true;
                }

                //Ab hier beginnt die eigentliche Logik der Daueraufträge als Einnahme

                if (einnahmeAusgabe) {

                    PreparedStatement pst = connection.prepareStatement("UPDATE Dauerauftrag SET dauerauftrag_datumabbuchung = ? WHERE user_dauerauftragid = ?");

                    Date zwischenDate = letztesDatumBuchung;

                    //Check ob monatlich, jährlich oder sonstiger Fall
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
                    } else if (jaehrlich) {
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

                //Ab hier beginnt die eigentliche Logik der Daueraufträge als Einnahme
                else {
                    PreparedStatement pst = connection.prepareStatement("UPDATE Dauerauftrag SET dauerauftrag_datumabbuchung = ? WHERE user_dauerauftragid = ?");
                    Date zwischenDate = letztesDatumBuchung;

                    //Check ob monatlich, jährlich oder sonstiger Fall
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

                    } else if (jaehrlich) {
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