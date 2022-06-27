package finanzmanager;

import java.sql.Date;


public class Ausgaben {

    private String ausgabenListBezeichnung;
    private float ausgabenListBetrag;
    private String ausgabenListDatum;

    private String ausgabeLabel;

    private int ausgabenId;


    public Ausgaben(String ausgabenListBezeichnung, float ausgabenListBetrag, String ausgabenListDatum, int ausgabenId) {
        this.ausgabenListBezeichnung = ausgabenListBezeichnung;
        this.ausgabenListBetrag = ausgabenListBetrag;
        this.ausgabenListDatum = ausgabenListDatum;
        this.ausgabenId = ausgabenId;
    }

    public int getAusgabenId() {
        return ausgabenId;
    }
    public float getAusgabenListBetrag() {
        return ausgabenListBetrag;
    }
    public String getAusgabenListBezeichnung() {
        return ausgabenListBezeichnung;
    }
    public String getAusgabenListDatum() { return ausgabenListDatum; }























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
