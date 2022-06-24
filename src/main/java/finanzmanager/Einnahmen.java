package finanzmanager;

public class Einnahmen {

    private String einnahmenListBezeichnung;
    private float einnahmenListBetrag;
    private String einnahmenListDatum;

    public Einnahmen(String einnahmenListBezeichnung, Float einnahmenListBetrag, String einnahmenListDatum) {
        this.einnahmenListBezeichnung = einnahmenListBezeichnung;
        this.einnahmenListBetrag = einnahmenListBetrag;
        this.einnahmenListDatum = einnahmenListDatum;
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
