package finanzmanager;

public class Dauerauftraege {
    private String dauerauftraegeListBezeichnung;
    private float dauerauftraegeListBetrag;
    private String dauerauftraegeListDatum;
    private String dauerauftraegeListDauer;

    private int dauerauftaegeId;

    public Dauerauftraege(String dauerauftraegeListBezeichnung, float dauerauftraegeListBetrag, String dauerauftraegeListDatum, String dauerauftraegeListDauer, int dauerauftaegeId) {
        this.dauerauftraegeListBezeichnung = dauerauftraegeListBezeichnung;
        this.dauerauftraegeListBetrag = dauerauftraegeListBetrag;
        this.dauerauftraegeListDatum = dauerauftraegeListDatum;
        this.dauerauftraegeListDauer = dauerauftraegeListDauer;
        this.dauerauftaegeId = dauerauftaegeId;
    }


    public int getDauerauftaegeId() {
        return dauerauftaegeId;
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
