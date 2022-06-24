package modelclasses;

import database.GetPostgresData;
import finanzmanager.Controller;

import java.sql.Date;


public class Ausgaben {

    private String ausgabenListBezeichnung;
    private float ausgabenListBetrag;
    private Date ausgabenListDate;

    private String ausgabeLabel;


    public Ausgaben( float ausgabenListBetrag, String ausgabenListBezeichnung, Date ausgabenListDate)
    {
        this.ausgabenListBetrag = ausgabenListBetrag;
        this.ausgabenListBezeichnung = ausgabenListBezeichnung;
        this.ausgabenListDate = ausgabenListDate;

    }


    public float getAusgabenListBetrag() {
        return ausgabenListBetrag;
    }
    public String getAusgabenListBezeichnung() {
        return ausgabenListBezeichnung;
    }
    public Date getAusgabenListDate() { return ausgabenListDate; }























  /* public static void ausgabeHinzufuegen(String ausgabenBetrag, String ausgabenBezeichnung, LocalDate ausgabenDate, String ausgabenKategorie) throws SQLException {

     /*
        //super.ausgabeHinzufuegenBtn(new ActionEvent());
        //super.setLabelAusgaben(ausgabeLabel);
        if (Objects.equals(ausgabenBetrag, ""))
        {
            ausgabeLabel = "Kein Betrag";

        }
        else if(Objects.equals(ausgabenBezeichnung, "")) {
            ausgabeLabel = "Keine Bezeichnung";

        }
        else if (Objects.equals(ausgabenDate, "")) {
            ausgabeLabel = "Kein Datum";

        } else {
            JavaPostgres.writeToDatabaseAusgaben(ausgabenBetrag, ausgabenBezeichnung, ausgabenDate);
    }

}*/

}