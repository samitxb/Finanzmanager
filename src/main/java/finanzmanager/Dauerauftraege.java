/*
 * Klasse für Daueraufträge
 */
package finanzmanager;


/**
 * Klasse Daueraufträge beinhaltet den Constructor und die Getter für Daueraufträge.
 * Wird benötigt für die Tableview.
 *
 * @author Max Weichselgartner
 * @version 1.0
 */
public class Dauerauftraege {
    private final String dauerauftraegeListBezeichnung;
    private final float dauerauftraegeListBetrag;
    private final String dauerauftraegeListDatum;
    private final String dauerauftraegeListDauer;
    private final int dauerauftraegeId;

    /**
     * Konstruktor der Daueraufträge.
     *
     * @param dauerauftraegeListBezeichnung Bezeichnung des Dauerauftrags.
     * @param dauerauftraegeListBetrag      Betrag des Dauerauftrags.
     * @param dauerauftraegeListDatum       Datum des Dauerauftrags.
     * @param dauerauftraegeListDauer       Abstand des Dauerauftrags.
     * @param dauerauftaegeId               ID des Dauerauftrags.
     */
    public Dauerauftraege(String dauerauftraegeListBezeichnung, float dauerauftraegeListBetrag, String dauerauftraegeListDatum, String dauerauftraegeListDauer, int dauerauftaegeId) {
        this.dauerauftraegeListBezeichnung = dauerauftraegeListBezeichnung;
        this.dauerauftraegeListBetrag = dauerauftraegeListBetrag;
        this.dauerauftraegeListDatum = dauerauftraegeListDatum;
        this.dauerauftraegeListDauer = dauerauftraegeListDauer;
        this.dauerauftraegeId = dauerauftaegeId;
    }

    /**
     * Getter des DauerauftragID.
     *
     * @return ID des Dauerauftrags.
     */
    public int getDauerauftraegeId() {
        return dauerauftraegeId;
    }

    /**
     * Getter der Dauer des Dauerauftrags.
     *
     * @return Dauer/Abstand des Dauerauftrags.
     */
    public String getDauerauftraegeListDauer() {
        return dauerauftraegeListDauer;
    }

    /**
     * Getter der Bezeichnung des Dauerauftrags.
     *
     * @return Bezeichnung des Dauerauftrags.
     */
    public String getDauerauftraegeListBezeichnung() {
        return dauerauftraegeListBezeichnung;
    }

    /**
     * Getter des Betrags des Dauerauftrags.
     *
     * @return Betrag des Dauerauftrags.
     */
    public float getDauerauftraegeListBetrag() {
        return dauerauftraegeListBetrag;
    }

    /**
     * Getter des Datums des Dauerauftrags.
     *
     * @return Datum des Dauerauftrags.
     */
    public String getDauerauftraegeListDatum() {
        return dauerauftraegeListDatum;
    }
}
