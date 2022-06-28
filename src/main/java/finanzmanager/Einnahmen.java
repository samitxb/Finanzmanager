package finanzmanager;


/**
 * Klasse Einnahmen beinhaltet den Constructor und die Getter für Einnahmen.
 * Wird benötigt für die Tableview.
 *
 * @author Max Weichselgartner
 * @version 1.0
 *
 */
public class Einnahmen {

    private String einnahmenListBezeichnung;
    private float einnahmenListBetrag;
    private String einnahmenListDatum;
    private int einnahmenId;

    /**
     * Constructor der Einnahmen.
     * @param einnahmenListBezeichnung Bezeichnung der Einnahme.
     * @param einnahmenListBetrag Betrag der Einnahme.
     * @param einnahmenListDatum Datum der Einnahme.
     * @param einnahmenId ID der Einnahme.
     */
    public Einnahmen(String einnahmenListBezeichnung, float einnahmenListBetrag, String einnahmenListDatum, int einnahmenId) {
        this.einnahmenListBezeichnung = einnahmenListBezeichnung;
        this.einnahmenListBetrag = einnahmenListBetrag;
        this.einnahmenListDatum = einnahmenListDatum;
        this.einnahmenId = einnahmenId;
    }

    /**
     * Getter der EinnahmenId.
     * @return Id der Einnahme.
     */
    public int getEinnahmenId() {
        return einnahmenId;
    }

    /**
     * Getter der Bezeichnung der Einnahme.
     * @return Bezeichnung der Einnahme.
     */
    public String getEinnahmenListBezeichnung() {
        return einnahmenListBezeichnung;
    }

    /**
     * Getter des Betrags der Einnahme.
     * @return Betrag der Einnahme.
     */
    public float getEinnahmenListBetrag() {
        return einnahmenListBetrag;
    }

    /**
     * Getter des Datums der Einnahme.
     * @return Datum der Einnahme.
     */
    public String getEinnahmenListDatum() {
        return einnahmenListDatum;
    }
}
