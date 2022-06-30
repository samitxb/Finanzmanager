/*
 * Klasse für Ausgaben
 */
package finanzmanager;

/**
 * Klasse Ausgaben beinhaltet den Constructor und die Getter für Ausgaben.
 * Wird benötigt für die Tableview.
 *
 * @author Max Weichselgartner
 * @version 1.0
 */
public class Ausgaben {

    private final String ausgabenListBezeichnung;
    private final float ausgabenListBetrag;
    private final String ausgabenListDatum;
    private final int ausgabenId;

    /**
     * Konstruktor für die Ausgaben.
     *
     * @param ausgabenListBezeichnung Bezeichnung der Ausgabe.
     * @param ausgabenListBetrag      Betrag der Ausgabe.
     * @param ausgabenListDatum       Datum der Ausgabe.
     * @param ausgabenId              ID der Ausgabe.
     */
    public Ausgaben(String ausgabenListBezeichnung, float ausgabenListBetrag, String ausgabenListDatum, int ausgabenId) {
        this.ausgabenListBezeichnung = ausgabenListBezeichnung;
        this.ausgabenListBetrag = ausgabenListBetrag;
        this.ausgabenListDatum = ausgabenListDatum;
        this.ausgabenId = ausgabenId;
    }

    /**
     * Getter der AusgabenId.
     *
     * @return ID der Ausgabe.
     */
    public int getAusgabenId() {
        return ausgabenId;
    }

    /**
     * Getter des Betrags der Ausgabe.
     *
     * @return Betrag der Ausgabe.
     */
    public float getAusgabenListBetrag() {
        return ausgabenListBetrag;
    }

    /**
     * Getter der Bezeichnung der Ausgabe.
     *
     * @return Bezeichnung der Ausgabe.
     */
    public String getAusgabenListBezeichnung() {
        return ausgabenListBezeichnung;
    }

    /**
     * Getter des Datums der Ausgabe.
     *
     * @return Datum der Ausgabe.
     */
    public String getAusgabenListDatum() {
        return ausgabenListDatum;
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
