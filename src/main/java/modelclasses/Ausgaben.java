package modelclasses;

import finanzmanager.Controller;

import java.sql.Date;


public class Ausgaben {
    private static int  ausgabenListID;
    private static String ausgabenListBezeichnung;
    private static float ausgabenListBetrag;


    private Date ausgabenDate;

    private String ausgabeLabel;


    public Ausgaben(int ausgabenListID, String ausgabenListBezeichnung, float ausgabenListBetrag) {
        Ausgaben.ausgabenListID = ausgabenListID;
        Ausgaben.ausgabenListBezeichnung = ausgabenListBezeichnung;
        Ausgaben.ausgabenListBetrag = ausgabenListBetrag;
    }

    public int getAusgabenListID() {
        return ausgabenListID;
    }

    public String getAusgabenListBezeichnung() {
        return ausgabenListBezeichnung;
    }

    public float getAusgabenListBetrag() {
        return ausgabenListBetrag;
    }

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