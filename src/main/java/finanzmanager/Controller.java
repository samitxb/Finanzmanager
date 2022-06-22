package finanzmanager;

import database.JavaPostgres;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelclasses.NurNummern;

import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.Objects;


public class Controller {


    //-----------------Ausgaben Reiter--------------------------------
    @FXML
    private TextField ausgabenBetrag;

    @FXML
    /**
     * Textfeld zur Eingabe vom Datum der Ausgabe
     */
    private DatePicker ausgabenDate;

    @FXML
    private TextField ausgabenBezeichnung;

    @FXML
    private Label labelAusgaben;

    //----------------------------------------------------------------


    //-----------------Einnahmen Reiter--------------------------------
    @FXML
    private TextField einnahmenBetrag;

    @FXML
    private DatePicker einnahmenDate;

    @FXML
    private TextField einnahmenBezeichnung;

    @FXML
    private Label labelEinnahmen;

    //----------------------------------------------------------------

    //--------------------Daueraufträge ------------------------------
    @FXML
    private MenuButton menubarZeitspanneDauerauftrag;

    @FXML
    private TextField dauerauftragBezeichnung;

    @FXML
    private CheckBox dauerauftragAusgabe;

    @FXML
    private TextField dauerauftragBetrag;

    @FXML
    private CheckBox dauerauftragEinnahme;

    @FXML
    private TextField dauerauftragZeitspanneText;

    @FXML
    private DatePicker dauerauftragDate;

    @FXML
    private Label labelDauerauftraege;

    //----------------------------------------------------------------

    @FXML
    private Button quitBtn;

    //----------------------------------------------------------------


    @FXML
    /**
     * Bei betätigen des Quit Buttons wird man zurück in den LoginScreen geworfen.
     */
    void quitApp(ActionEvent event) throws IOException {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        primaryStage.setTitle("User Login");
        primaryStage.setScene(new Scene(root, 657, 532));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    public void enterSettings(ActionEvent actionEvent) throws IOException {
        Stage secondaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Settings.fxml")));
        secondaryStage.setTitle("Einstellungen");
        secondaryStage.setScene(new Scene(root, 400, 500));
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.show();
    }
    @FXML
    public void ausgabeHinzufuegenBtn(ActionEvent actionEvent) throws SQLException {
        System.out.println(ausgabenBetrag.getText());
        System.out.println(ausgabenBezeichnung.getText());


        LocalDate localDate = ausgabenDate.getValue();
        System.out.println(localDate);

        /*LocalDate isoDate = ausgabenDate.getValue();
        ChronoLocalDate chronoDate = ((isoDate != null) ? ausgabenDate.getChronology().date(isoDate) : null);
        System.err.println("Selected date: " + chronoDate);*/


        if (Objects.equals(ausgabenBetrag.getText(), "")) {
            labelAusgaben.setText("Kein Betrag!");
        } else if (Objects.equals(ausgabenBezeichnung.getText(), "")) {
            labelAusgaben.setText("Keine Bezeichnung !");
        } else if (Objects.equals(localDate, null)) {
            labelAusgaben.setText("Kein Datum!");
        } else {
            JavaPostgres.writeToDatabaseAusgaben(Float.valueOf(ausgabenBetrag.getText()), ausgabenBezeichnung.getText(), Date.valueOf(localDate));

            ausgabenBetrag.clear();
            ausgabenBezeichnung.clear();
            ausgabenDate.setValue(null);


        }
    }

    /* Für Einnahmen alles */
    @FXML
    void einnahmeHinzufuegenBtn(ActionEvent event) throws SQLException {
        System.out.println(einnahmenBetrag.getText());
        System.out.println(einnahmenBezeichnung.getText());

        LocalDate localDate = einnahmenDate.getValue();
        System.out.println(localDate);

        if (Objects.equals(einnahmenBetrag.getText(), "")) {
            labelEinnahmen.setText("Kein Betrag!");
        } else if (Objects.equals(einnahmenBezeichnung.getText(), "")) {
            labelEinnahmen.setText("Keine Bezeichnung!");
        } else if (Objects.equals(localDate, null)) {
            labelEinnahmen.setText("Kein Datum!");
        } else {
            JavaPostgres.writeToDatabaseEinnahmen(Float.valueOf(einnahmenBetrag.getText()), einnahmenBezeichnung.getText(), Date.valueOf(localDate));

            einnahmenBetrag.clear();
            einnahmenBezeichnung.clear();
            einnahmenDate.setValue(null);


        }
    }

    @FXML
    public void dauerauftragHinzufuegenBtn(ActionEvent actionEvent) throws SQLException {
        System.out.println(dauerauftragBetrag.getText());
        System.out.println(dauerauftragBezeichnung.getText());
        System.out.println(dauerauftragZeitspanneText.getText());

        LocalDate localDate = dauerauftragDate.getValue();
        System.out.println(localDate);

        if (Objects.equals(dauerauftragBetrag.getText(), "")) {
            labelDauerauftraege.setText("Kein Betrag!");
        } else if (Objects.equals(dauerauftragBezeichnung.getText(), "")) {
            labelDauerauftraege.setText("Keine Bezeichnung!");
        } else if (Objects.equals(localDate, null)) {
            labelDauerauftraege.setText("Kein Datum!");
        } else if (Objects.equals(menubarZeitspanneDauerauftrag.getText(), "")) {
            labelDauerauftraege.setText("Keine Zeitspanne!");
        } else {
            JavaPostgres.writeToDatabaseDauerauftrag(Float.valueOf(dauerauftragBetrag.getText()), dauerauftragBezeichnung.getText(), Date.valueOf(localDate), dauerauftragZeitspanneText.getText());

            dauerauftragBetrag.clear();
            dauerauftragBezeichnung.clear();
            einnahmenDate.setValue(null);
            dauerauftragZeitspanneText.clear();


        }
    }

    //----------------------------------------------------------------
    public void taeglich(ActionEvent actionEvent) {
        dauerauftragZeitspanneText.setText("Täglich");
    }

    public void woechentlich(ActionEvent actionEvent) {
        dauerauftragZeitspanneText.setText("Wöchentlich");
    }

    public void monatlich(ActionEvent actionEvent) {
        dauerauftragZeitspanneText.setText("Monatlich");
    }

    public void jaehrlich(ActionEvent actionEvent) {
        dauerauftragZeitspanneText.setText("Jährlich");
    }
    //----------------------------------------------------------------

}
