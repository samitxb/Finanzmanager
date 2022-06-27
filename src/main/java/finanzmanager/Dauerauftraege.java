package finanzmanager;

public class Dauerauftraege {
    private String dauerauftraegeListBezeichnung;
    private float dauerauftraegeListBetrag;
    private String dauerauftraegeListDatum;
    private String dauerauftraegeListDauer;

    private int dauerauftraegeId;

    public Dauerauftraege(String dauerauftraegeListBezeichnung, float dauerauftraegeListBetrag, String dauerauftraegeListDatum, String dauerauftraegeListDauer, int dauerauftaegeId) {
        this.dauerauftraegeListBezeichnung = dauerauftraegeListBezeichnung;
        this.dauerauftraegeListBetrag = dauerauftraegeListBetrag;
        this.dauerauftraegeListDatum = dauerauftraegeListDatum;
        this.dauerauftraegeListDauer = dauerauftraegeListDauer;
        this.dauerauftraegeId = dauerauftaegeId;
    }


    public int getDauerauftraegeId() {
        return dauerauftraegeId;
    }

    public String getDauerauftraegeListDauer() {
        return dauerauftraegeListDauer;
    }

    public String getDauerauftraegeListBezeichnung() {
        return dauerauftraegeListBezeichnung;
    }

    public float getDauerauftraegeListBetrag() {
        return dauerauftraegeListBetrag;
    }

    public String getDauerauftraegeListDatum() {
        return dauerauftraegeListDatum;
    }
}
