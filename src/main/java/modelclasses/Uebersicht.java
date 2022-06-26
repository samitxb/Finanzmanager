package modelclasses;

import database.JavaPostgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Uebersicht {
    protected static float gesamtAusgaben;
    protected static float gesamtEinnahmen;

    protected static float aktuellerKontostand;


 public static float ausgabenZusammenRechnen()  {

     int id = UserLogin.id;


     try {
         JavaPostgres javaPostgres = new JavaPostgres();

         Connection con = javaPostgres.getConnection();
         PreparedStatement pst = con.prepareStatement("SELECT Sum(ausgaben_betrag) as ausgabengesamt FROM ausgaben where user_ausgabenid=?");
         pst.setInt(1, id);
         ResultSet rs = pst.executeQuery();
         if(rs.next())
         {
             gesamtAusgaben = rs.getFloat("ausgabengesamt");
         }


     }

     catch (SQLException e) {
         throw new RuntimeException(e);
     }

        System.out.println(gesamtAusgaben);
     return gesamtAusgaben;
 }



 public static float einnahmenZusammenRechnen(){


     int id = UserLogin.id;


     try {
         JavaPostgres javaPostgres = new JavaPostgres();

         Connection con = javaPostgres.getConnection();
         PreparedStatement pst = con.prepareStatement("SELECT Sum(einnahmen_betrag) as einnahmengesamt FROM einnahmen where user_einnahmenid=?");
         pst.setInt(1, id);
         ResultSet rs = pst.executeQuery();
         if(rs.next())
         {
             gesamtEinnahmen = rs.getFloat("einnahmengesamt");
         }


     }

     catch (SQLException e) {
         throw new RuntimeException(e);
     }

     System.out.println(gesamtEinnahmen);

     return gesamtEinnahmen;
 }

 public static float aktuellerKontostandZusammen(){

     int id = UserLogin.id;


     try {
         JavaPostgres javaPostgres = new JavaPostgres();

         Connection con = javaPostgres.getConnection();
         PreparedStatement pst = con.prepareStatement("SELECT kontostand FROM userinfo where userid=?");
         pst.setInt(1, id);
         ResultSet rs = pst.executeQuery();
         if(rs.next())
         {
             aktuellerKontostand = rs.getFloat("kontostand");
         }


     }

     catch (SQLException e) {
         throw new RuntimeException(e);
     }

     gesamtEinnahmen=einnahmenZusammenRechnen();
     gesamtAusgaben=ausgabenZusammenRechnen();
     aktuellerKontostand = aktuellerKontostand - gesamtAusgaben + gesamtEinnahmen;

     return aktuellerKontostand;

 }

}
