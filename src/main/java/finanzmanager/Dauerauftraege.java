package finanzmanager;

public class Dauerauftraege {
    private String dauerauftraegeListBezeichnung;
    private float dauerauftraegeListBetrag;
    private String dauerauftraegeListDatum;
    private String dauerauftraegeListDauer;

    public Dauerauftraege(String dauerauftraegeListBezeichnung, float dauerauftraegeListBetrag, String dauerauftraegeListDatum, String dauerauftraegeListDauer) {
        this.dauerauftraegeListBezeichnung = dauerauftraegeListBezeichnung;
        this.dauerauftraegeListBetrag = dauerauftraegeListBetrag;
        this.dauerauftraegeListDatum = dauerauftraegeListDatum;
        this.dauerauftraegeListDauer = dauerauftraegeListDauer;
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
