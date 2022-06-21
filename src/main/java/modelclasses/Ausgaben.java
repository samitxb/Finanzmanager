package modelclasses;

import database.JavaPostgres;
import finanzmanager.Controller;
import javafx.event.ActionEvent;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class Ausgaben extends Controller {
   /* @Override
    public void ausgabeHinzufuegenBtn(ActionEvent actionEvent) throws SQLException {
        super.ausgabeHinzufuegenBtn(actionEvent);
        System.out.println(ausgabenBetrag.getText());
    }*/

    float ausgabenBetrag;
    String ausgabenBezeichnung;
    Date ausgabenDate;
    String ausgabenKategorie;
    static String ausgabeLabel;

    public static void ausgabeHinzufuegen(String ausgabenBetrag, String ausgabenBezeichnung, LocalDate ausgabenDate, String ausgabenKategorie) throws SQLException {

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
    }*/

}

}