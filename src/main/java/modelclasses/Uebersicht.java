package modelclasses;

import database.JavaPostgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Klasse Übersicht wird benötigt für berechnung des gesamtEinnahmen, gesamtAusgaben und des aktuellen Kontostands.
 *
 * @author Max Weichselgartner, Michael Irlmeier
 * @version 1.0
 */
public class Uebersicht {
    protected static float gesamtAusgaben;
    protected static float gesamtEinnahmen;
    protected static float aktuellerKontostand;


    static JavaPostgres javaPostgres = new JavaPostgres();
    static Connection con = javaPostgres.getConnection();

    /**
     * Rechnet alle Ausgaben des eingeloggten Benutzers zusammen. Die Daten werden aus der Datenbank geholt.
     *
     * @return gesamtAusgaben des eingeloggten Benutzers.
     */
    public static float ausgabenZusammenRechnen() {

        int id = UserLogin.id;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT Sum(ausgaben_betrag) as ausgabengesamt FROM ausgaben where user_ausgabenid=?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                gesamtAusgaben = rs.getFloat("ausgabengesamt");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(gesamtAusgaben);
        return gesamtAusgaben;
    }

    /**
     * Rechnet alle Einnahmen des eingeloggten Benutzers zusammen. Die Daten werden aus der Datenbank geholt.
     *
     * @return gesamtEinnahmen des eingeloggten Benutzers.
     */
    public static float einnahmenZusammenRechnen() {

        int id = UserLogin.id;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT Sum(einnahmen_betrag) as einnahmengesamt FROM einnahmen where user_einnahmenid=?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                gesamtEinnahmen = rs.getFloat("einnahmengesamt");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(gesamtEinnahmen);
        return gesamtEinnahmen;
    }

    /**
     * Holt den Kontostand aus der Datenbank und rechnet den aktuellen Kontostand aus.
     *
     * @return aktuellenKontostand des eingeloggten Benutzers.
     */
    public static float aktuellerKontostandZusammen() {

        int id = UserLogin.id;
        JavaPostgres javaPostgres = new JavaPostgres();
        Connection con = javaPostgres.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT kontostand FROM userinfo where userid=?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                aktuellerKontostand = rs.getFloat("kontostand");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        gesamtEinnahmen = einnahmenZusammenRechnen();
        gesamtAusgaben = ausgabenZusammenRechnen();

        aktuellerKontostand = aktuellerKontostand - gesamtAusgaben + gesamtEinnahmen;

        return aktuellerKontostand;

    }
}
