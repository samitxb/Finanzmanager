package finanzmanager;

public class Einnahmen {

    private String einnahmenListBezeichnung;
    private float einnahmenListBetrag;
    private String einnahmenListDatum;

    private int einnahmenId;

    public Einnahmen(String einnahmenListBezeichnung, float einnahmenListBetrag, String einnahmenListDatum, int einnahmenId) {
        this.einnahmenListBezeichnung = einnahmenListBezeichnung;
        this.einnahmenListBetrag = einnahmenListBetrag;
        this.einnahmenListDatum = einnahmenListDatum;
        this.einnahmenId = einnahmenId;
    }

    public int getEinnahmenId() {
        return einnahmenId;
    }

    public String getEinnahmenListBezeichnung() {
        return einnahmenListBezeichnung;
    }

    public float getEinnahmenListBetrag() {
        return einnahmenListBetrag;
    }

    public String getEinnahmenListDatum() {
        return einnahmenListDatum;
    }
}
