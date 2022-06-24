package modelclasses;


public class AusgabenTest {

    public String ausgabenListBezeichnung;
    public float ausgabenListBetrag;
    public String ausgabenListDate;

    public String ausgabeLabel;


    public AusgabenTest(float ausgabenListBetrag, String ausgabenListBezeichnung, String ausgabenListDate)
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
    public String getAusgabenListDate() { return ausgabenListDate; }























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