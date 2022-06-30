/*
 *  Diese Klasse dient als Controller für die ActualView
 */
package finanzmanager;

import database.JavaPostgres;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelclasses.NurNummern;
import modelclasses.Uebersicht;
import modelclasses.UserLogin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.JavaPostgres.databaseConnectionLink;


/**
 * Klasse Controller ist der Controller für die ActualView.fxml
 *
 * @author Max Weichselgartner, Michael Irlmeier
 * @version 1.0
 */
public class Controller implements Initializable {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public Float kontostand;
    public Float gesamtAusgaben;
    public Float gesamtEinnahmen;

    // UserLogin Id zur Abfrage, welche Daten aus der Datenbank geholt werden sollen
    int id = UserLogin.id;

    /*=======================================ObservableList=======================================*/
    ObservableList<Ausgaben> oblistAusgaben = FXCollections.observableArrayList();
    ObservableList<Dauerauftraege> oblistDauerauftraege = FXCollections.observableArrayList();
    ObservableList<Einnahmen> oblistEinnahmen = FXCollections.observableArrayList();

    /*=======================================Ausgaben=======================================*/
    @FXML
    private TextField ausgabenBetrag;
    @FXML
    private DatePicker ausgabenDate;
    @FXML
    private TextField ausgabenBezeichnung;
    @FXML
    private Label labelAusgaben;
    @FXML
    private TableView<Ausgaben> ausgabenView;
    @FXML
    private TableColumn<Ausgaben, Float> ausgabenListBetrag;
    @FXML
    private TableColumn<Ausgaben, String> ausgabenListBezeichnung;
    @FXML
    private TableColumn<Ausgaben, String> ausgabenListDatum;
    @FXML
    private TableView<Ausgaben> ausgabenViewUebersicht;
    @FXML
    private TableColumn<Ausgaben, Float> ausgabenListBetragUebersicht;
    @FXML
    private TableColumn<Ausgaben, String> ausgabenListBezeichnungUebersicht;
    @FXML
    private TableColumn<Ausgaben, String> ausgabenListDatumUebersicht;
    @FXML
    private Button ausgabenListLoeschenBtn;

    /*=======================================Daueraufträge=======================================*/
    @FXML
    private TableColumn<Dauerauftraege, Float> dauerauftraegeListBetrag;
    @FXML
    private TableColumn<Dauerauftraege, String> dauerauftraegeListBezeichnung;
    @FXML
    private TableColumn<Dauerauftraege, String> dauerauftraegeListDatum;
    @FXML
    private TableView<Dauerauftraege> dauerauftraegeView;
    @FXML
    private TableColumn<Dauerauftraege, String> dauerauftraegeListDauer;
    @FXML
    private Button dauerauftragListLoeschenBtn;

    /*=======================================Einnahmen=======================================*/
    @FXML
    private TableView<Einnahmen> einnahmenViewUebersicht;
    @FXML
    private TableColumn<Einnahmen, Float> einnahmenListBetragUebersicht;
    @FXML
    private TableColumn<Einnahmen, String> einnahmenListBezeichnungUebersicht;
    @FXML
    private TableColumn<Einnahmen, String> einnahmenListDatumUebersicht;
    @FXML
    private TableColumn<Einnahmen, Float> einnahmenListBetrag;
    @FXML
    private TableColumn<Einnahmen, String> einnahmenListBezeichnung;
    @FXML
    private TableColumn<Einnahmen, String> einnahmenListDatum;
    @FXML
    private TableView<Einnahmen> einnahmenView;
    @FXML
    private Button einnahmenListLoeschenBtn;
    @FXML
    private TextField einnahmenBetrag;
    @FXML
    private DatePicker einnahmenDate;
    @FXML
    private TextField einnahmenBezeichnung;
    @FXML
    private Label labelEinnahmen;
    @FXML
    private TextField aktuellerKontostandUebersicht;
    @FXML
    private TextField gesamtAusgabenUebersicht;
    @FXML
    private TextField gesamtEinnahmenUebersicht;

    /*=======================================Daueraufträge=======================================*/
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

    /*=======================================Quit=======================================*/
    @FXML
    private Button quitBtn;

    /*=======================================Export=======================================*/
    @FXML
    private CheckBox exportAlles;
    @FXML
    private CheckBox exportAusgaben;
    @FXML
    private CheckBox exportEinnahmen;
    @FXML
    private Button exportExportierenBtn;
    @FXML
    private TextField exportName;
    @FXML
    private Label exportSpeicherort;
    @FXML
    private Button exportWo;
    @FXML
    private Label labelExport;


    /**
     * Beim start des Controllers werden diese Funktionen ausgeführt.
     *
     * @param url            .
     * @param resourceBundle .
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ladeDatenAusgaben();
        ladeDatenEinnahmen();
        ladeDatenDauerauftrag();

        try {
            DauerauftragLogik.dauerauftragKontrolle();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        NurNummern.numericOnly(ausgabenBetrag);
        NurNummern.numericOnly(einnahmenBetrag);
        NurNummern.numericOnly(dauerauftragBetrag);
    }


    /**
     * Beim Ausführen der Funktion, werden alle Ausgabendaten in eine ObservableList geladen, und dann in den Tableviews angezeigt
     */
    public void ladeDatenAusgaben() {
        JavaPostgres javaPostgres = new JavaPostgres();
        javaPostgres.getConnection();

        try {
            Connection con = databaseConnectionLink;
            PreparedStatement pstAusgaben = con.prepareStatement("SELECT * FROM ausgaben WHERE user_ausgabenid=?");
            pstAusgaben.setInt(1, id);
            ResultSet rs = pstAusgaben.executeQuery();

            while (rs.next()) {
                oblistAusgaben.add(new Ausgaben(rs.getString("ausgaben_bezeichnung"), rs.getFloat("ausgaben_betrag"), rs.getString("ausgaben_datum"), rs.getInt("ausgabenid")));
            }
        } catch (SQLException exception) {
            Logger.getLogger(Ausgaben.class.getName()).log(Level.SEVERE, null, exception);
        }
        ausgabenListDatum.setCellValueFactory(new PropertyValueFactory<Ausgaben, String>("ausgabenListDatum"));
        ausgabenListBezeichnung.setCellValueFactory(new PropertyValueFactory<Ausgaben, String>("ausgabenListBezeichnung"));
        ausgabenListBetrag.setCellValueFactory(new PropertyValueFactory<Ausgaben, Float>("ausgabenListBetrag"));

        ausgabenListDatumUebersicht.setCellValueFactory(new PropertyValueFactory<Ausgaben, String>("ausgabenListDatum"));
        ausgabenListBezeichnungUebersicht.setCellValueFactory(new PropertyValueFactory<Ausgaben, String>("ausgabenListBezeichnung"));
        ausgabenListBetragUebersicht.setCellValueFactory(new PropertyValueFactory<Ausgaben, Float>("ausgabenListBetrag"));

        ausgabenView.setItems(oblistAusgaben);
        ausgabenViewUebersicht.setItems(oblistAusgaben);
        ladeKontodaten();
    }


    /**
     * Beim Ausführen der Funktion, werden alle Einnahmendaten in eine ObservableList geladen, und dann in den Tableviews angezeigt.
     */
    public void ladeDatenEinnahmen() {
        JavaPostgres javaPostgres = new JavaPostgres();
        javaPostgres.getConnection();
        try {
            Connection con = databaseConnectionLink;
            PreparedStatement pstEinnahmen = con.prepareStatement("SELECT * FROM einnahmen WHERE user_einnahmenid=?");
            pstEinnahmen.setInt(1, id);
            ResultSet rs = pstEinnahmen.executeQuery();

            while (rs.next()) {
                oblistEinnahmen.add(new Einnahmen(rs.getString("einnahmen_bezeichnung"), rs.getFloat("einnahmen_betrag"), rs.getString("einnahmen_datum"), rs.getInt("einnahmenid")));
            }
        } catch (SQLException exception) {
            Logger.getLogger(Einnahmen.class.getName()).log(Level.SEVERE, null, exception);
        }
        einnahmenListDatum.setCellValueFactory(new PropertyValueFactory<Einnahmen, String>("einnahmenListDatum"));
        einnahmenListBezeichnung.setCellValueFactory(new PropertyValueFactory<Einnahmen, String>("einnahmenListBezeichnung"));
        einnahmenListBetrag.setCellValueFactory(new PropertyValueFactory<Einnahmen, Float>("einnahmenListBetrag"));

        einnahmenListDatumUebersicht.setCellValueFactory(new PropertyValueFactory<Einnahmen, String>("einnahmenListDatum"));
        einnahmenListBezeichnungUebersicht.setCellValueFactory(new PropertyValueFactory<Einnahmen, String>("einnahmenListBezeichnung"));
        einnahmenListBetragUebersicht.setCellValueFactory(new PropertyValueFactory<Einnahmen, Float>("einnahmenListBetrag"));

        einnahmenView.setItems(oblistEinnahmen);
        einnahmenViewUebersicht.setItems(oblistEinnahmen);
        ladeKontodaten();
    }


    /**
     * Beim Ausführen der Funktion, werden alle Dauerauftragsdaten in eine ObservableList geladen, und dann in den Tableviews angezeigt.
     */
    public void ladeDatenDauerauftrag() {
        try {
            Connection con = databaseConnectionLink;
            PreparedStatement pstDauerauftrag = con.prepareStatement("SELECT * FROM dauerauftrag WHERE user_dauerauftragid=?");
            pstDauerauftrag.setInt(1, id);
            ResultSet rs = pstDauerauftrag.executeQuery();

            while (rs.next()) {
                oblistDauerauftraege.add(new Dauerauftraege(rs.getString("dauerauftrag_bezeichnung"), rs.getFloat("dauerauftrag_betrag"), rs.getString("dauerauftrag_datum"), rs.getString("dauerauftrag_zeitraum"), rs.getInt("dauerauftragid")));
            }
        } catch (SQLException exception) {
            Logger.getLogger(Einnahmen.class.getName()).log(Level.SEVERE, null, exception);
        }
        dauerauftraegeListDatum.setCellValueFactory(new PropertyValueFactory<Dauerauftraege, String>("dauerauftraegeListDatum"));
        dauerauftraegeListBezeichnung.setCellValueFactory(new PropertyValueFactory<Dauerauftraege, String>("dauerauftraegeListBezeichnung"));
        dauerauftraegeListBetrag.setCellValueFactory(new PropertyValueFactory<Dauerauftraege, Float>("dauerauftraegeListBetrag"));
        dauerauftraegeListDauer.setCellValueFactory(new PropertyValueFactory<Dauerauftraege, String>("dauerauftraegeListDauer"));

        dauerauftraegeView.setItems(oblistDauerauftraege);
        ladeKontodaten();
    }

    /**
     * Rechnet gesamtAusgaben, gesamtEinnahmen und aktuellenKontostand zusammen und gibt diese aus.
     */
    public void ladeKontodaten() {
        kontostand = Uebersicht.aktuellerKontostandZusammen();
        aktuellerKontostandUebersicht.setText((df.format(kontostand)) + " €");

        gesamtAusgaben = Uebersicht.ausgabenZusammenRechnen();
        gesamtAusgabenUebersicht.setText(df.format(gesamtAusgaben) + " €");

        gesamtEinnahmen = Uebersicht.einnahmenZusammenRechnen();
        gesamtEinnahmenUebersicht.setText(df.format(gesamtEinnahmen) + " €");
    }

    @FXML
    /**
     * Bei Betätigen des Quit Buttons wird man zurück in den LoginScreen geworfen.
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

    /**
     * Beim Drücken des Buttons Settings, öffnet sich das Fenster mit den Einstellungen
     *
     * @param actionEvent → wird beim drücken des Knopfes ausgeführt.
     * @throws IOException → wirft einen Fehler.
     */
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

    /**
     * Beim Drücken des Knopfes, wird abgefragt, ob alle Textfelder leer sind. Falls nicht,
     * werden die eingegebenen Daten in die Datenbank geschrieben und danach direkt in den Tableviews angezeigt.
     *
     * @param actionEvent → wird beim drücken des Knopfes ausgeführt.
     * @throws SQLException → wirft einen Fehler.
     */
    @FXML
    public void ausgabeHinzufuegenBtn(ActionEvent actionEvent) throws SQLException {
        boolean istdouble;

        System.out.println(ausgabenBetrag.getText());
        System.out.println(ausgabenBezeichnung.getText());

        LocalDate localDate = ausgabenDate.getValue();
        System.out.println(localDate);

        istdouble = NurNummern.isDouble(ausgabenBetrag.getText());

        if (Objects.equals(ausgabenBetrag.getText(), "")) {
            labelAusgaben.setText("Kein Betrag!");
        } else if (Objects.equals(ausgabenBezeichnung.getText(), "")) {
            labelAusgaben.setText("Keine Bezeichnung !");
        } else if (Objects.equals(localDate, null)) {
            labelAusgaben.setText("Kein Datum!");
        } else if (!istdouble) {
            labelAusgaben.setText("Keine Zahl!");
        } else {
            JavaPostgres.writeToDatabaseAusgaben(Float.valueOf(ausgabenBetrag.getText()), ausgabenBezeichnung.getText(), Date.valueOf(localDate));

            labelAusgaben.setText("Gespeichert!");
            ausgabenBetrag.clear();
            ausgabenBezeichnung.clear();
            ausgabenDate.setValue(null);

            oblistAusgaben.clear();
            ladeDatenAusgaben();
        }
    }

    /**
     * Beim Drücken des Knopfes, wird abgefragt, ob alle Textfelder leer sind. Falls nicht,
     * werden die eingegebenen Daten in die Datenbank geschrieben und danach direkt in den Tableviews angezeigt.
     *
     * @param event -> wird beim drücken des Knopfes ausgeführt.
     * @throws SQLException -> wirft einen Fehler
     */
    @FXML
    void einnahmeHinzufuegenBtn(ActionEvent event) throws SQLException {
        boolean istdouble;

        labelEinnahmen.setText(null);

        System.out.println(einnahmenBetrag.getText());
        System.out.println(einnahmenBezeichnung.getText());

        LocalDate localDate = einnahmenDate.getValue();
        System.out.println(localDate);

        istdouble = NurNummern.isDouble(einnahmenBetrag.getText());

        if (Objects.equals(einnahmenBetrag.getText(), "")) {
            labelEinnahmen.setText("Kein Betrag!");
        } else if (Objects.equals(einnahmenBezeichnung.getText(), "")) {
            labelEinnahmen.setText("Keine Bezeichnung!");
        } else if (Objects.equals(localDate, null)) {
            labelEinnahmen.setText("Kein Datum!");
        } else if (!istdouble) {
            labelEinnahmen.setText("Keine Zahl!");
        } else {
            JavaPostgres.writeToDatabaseEinnahmen(Float.valueOf(einnahmenBetrag.getText()), einnahmenBezeichnung.getText(), Date.valueOf(localDate));

            labelEinnahmen.setText("Gespeichert!");
            einnahmenBetrag.clear();
            einnahmenBezeichnung.clear();
            einnahmenDate.setValue(null);

            oblistEinnahmen.clear();
            ladeDatenEinnahmen();
        }
    }

    /**
     * Beim Drücken des Knopfes, wird abgefragt, ob alle Textfelder leer sind. Falls nicht,
     * werden die eingegebenen Daten in die Datenbank geschrieben und danach direkt in den Tableviews angezeigt.
     *
     * @param actionEvent → wird beim drücken des Knopfes ausgeführt.
     * @throws SQLException → wirft einen Fehler
     */
    @FXML
    public void dauerauftragHinzufuegenBtn(ActionEvent actionEvent) throws SQLException {
        boolean dauerauftrag_einnahme_ausgabe;
        boolean istdouble;

        labelDauerauftraege.setText(null);

        System.out.println(dauerauftragBetrag.getText());
        System.out.println(dauerauftragBezeichnung.getText());
        System.out.println(dauerauftragZeitspanneText.getText());

        LocalDate localDate = dauerauftragDate.getValue();
        System.out.println(localDate);

        istdouble = NurNummern.isDouble(dauerauftragBetrag.getText());

        if (Objects.equals(dauerauftragBetrag.getText(), "")) {
            labelDauerauftraege.setText("Kein Betrag!");
        } else if (Objects.equals(dauerauftragBezeichnung.getText(), "")) {
            labelDauerauftraege.setText("Keine Bezeichnung!");
        } else if (Objects.equals(localDate, null)) {
            labelDauerauftraege.setText("Kein Datum!");
        } else if (Objects.equals(dauerauftragZeitspanneText.getText(), "")) {
            labelDauerauftraege.setText("Keine Zeitspanne!");
        } else if (!istdouble) {
            labelDauerauftraege.setText("Keine Zahl!");
        } else if (!dauerauftragEinnahme.isSelected() && !dauerauftragAusgabe.isSelected()) {
            labelDauerauftraege.setText("Bitte Einnahme noch Ausgabe auswählen!");
        } else {
            dauerauftrag_einnahme_ausgabe = !dauerauftragAusgabe.isSelected();

            labelDauerauftraege.setText("Gespeichert!");

            JavaPostgres.writeToDatabaseDauerauftrag(Float.valueOf(dauerauftragBetrag.getText()), dauerauftragBezeichnung.getText(), Date.valueOf(localDate), dauerauftragZeitspanneText.getText(), dauerauftrag_einnahme_ausgabe);

            dauerauftragBetrag.clear();
            dauerauftragBezeichnung.clear();
            dauerauftragDate.setValue(null);
            dauerauftragZeitspanneText.clear();
            dauerauftragAusgabe.setSelected(false);
            dauerauftragEinnahme.setSelected(false);

            oblistDauerauftraege.clear();
            ladeDatenDauerauftrag();
        }
    }

    /**
     * Wenn man einen Eintrag aus der Tableview auswählt und auf Löschen drückt, wird der Eintrag sowohl in der Tableview,
     * als auch in der Datenbank gelöscht.
     *
     * @param actionEvent → wird beim drücken des Knopfes ausgeführt.
     * @throws SQLException → wirft einen Fehler
     */
    public void ausgabenListLoeschen(ActionEvent actionEvent) throws SQLException {
        Connection con = databaseConnectionLink;
        Ausgaben selectedItem = ausgabenView.getSelectionModel().getSelectedItem();
        PreparedStatement pst = con.prepareStatement("DELETE FROM ausgaben WHERE ausgabenid=?");
        pst.setInt(1, selectedItem.getAusgabenId());
        pst.executeUpdate();

        System.out.println(selectedItem.getAusgabenId());
        ausgabenView.getItems().remove(selectedItem);
        ladeKontodaten();
    }

    /**
     * Wenn man einen Eintrag aus der Tableview auswählt und auf Löschen drückt, wird der Eintrag sowohl in der Tableview,
     * als auch in der Datenbank gelöscht.
     *
     * @param actionEvent → wird beim drücken des Knopfes ausgeführt.
     * @throws SQLException → wirft einen Fehler
     */
    public void einnahmenListLoeschen(ActionEvent actionEvent) throws SQLException {
        Connection con = databaseConnectionLink;
        Einnahmen selectedItem = einnahmenView.getSelectionModel().getSelectedItem();
        PreparedStatement pst = con.prepareStatement("DELETE FROM einnahmen WHERE einnahmenid=?");
        pst.setInt(1, selectedItem.getEinnahmenId());
        pst.executeUpdate();

        System.out.println(selectedItem.getEinnahmenId());
        einnahmenView.getItems().remove(selectedItem);
        ladeKontodaten();
    }

    /**
     * Wenn man einen Eintrag aus der Tableview auswählt und auf Löschen drückt, wird der Eintrag sowohl in der Tableview,
     * als auch in der Datenbank gelöscht.
     *
     * @param actionEvent → wird beim Drücken des Knopfes ausgeführt.
     * @throws SQLException → wirft einen Fehler
     */
    public void dauerauftragListLoeschen(ActionEvent actionEvent) throws SQLException {
        Connection con = databaseConnectionLink;
        Dauerauftraege selectedItem = dauerauftraegeView.getSelectionModel().getSelectedItem();
        PreparedStatement pst = con.prepareStatement("DELETE FROM dauerauftrag WHERE dauerauftragid=?");
        pst.setInt(1, selectedItem.getDauerauftraegeId());
        pst.executeUpdate();

        System.out.println(selectedItem.getDauerauftraegeId());
        dauerauftraegeView.getItems().remove(selectedItem);
        ladeKontodaten();
    }

    /**
     * Beim Drücken des Knopfes, wird abgefragt, ob alle Textfelder leer sind. Falls nicht,
     * werden die eingegebenen Daten zu unserem PDFGenerator übergeben.
     *
     * @param event → wird beim drücken des Knopfes ausgeführt.
     * @throws SQLException → wirft einen Fehler
     */
    @FXML
    void exportierenBtnPressed(ActionEvent event) throws SQLException {
        if (!exportAlles.isSelected() && !exportEinnahmen.isSelected() && !exportAusgaben.isSelected()) {
            labelExport.setText("Keine Daten ausgewählt!");
        } else if (exportSpeicherort.getText().equals("")) {
            labelExport.setText("Kein Speicherort ausgewählt!");
        } else if (exportName.getText().equals("")) {
            labelExport.setText("Kein Name angegeben!");
        } else {
            if (exportAusgaben.isSelected()) {
                PDFGenerator.pdfGenAusgaben(exportSpeicherort.getText(), exportName.getText());
                labelExport.setText("Exportiert!");
            }
            if (exportEinnahmen.isSelected()) {
                PDFGenerator.pdfGenEinnahmen(exportSpeicherort.getText(), exportName.getText());
                labelExport.setText("Exportiert!");
            }
            if (exportAlles.isSelected()) {
                PDFGenerator.pdfGenAusgaben(exportSpeicherort.getText(), exportName.getText());
                PDFGenerator.pdfGenEinnahmen(exportSpeicherort.getText(), exportName.getText());
                labelExport.setText("Exportiert!");
            }
            exportSpeicherort.setText(null);
            exportAlles.setSelected(false);
            exportAusgaben.setSelected(false);
            exportEinnahmen.setSelected(false);
            exportName.clear();

            System.out.println(exportSpeicherort.getText() + "\n" + exportName.getText());
        }
    }

    /**
     * Überprüft, welche CheckBoxes im Export aktiviert sind. Wenn exportAlles ausgewählt ist, kann man exportAusgaben und exportEinnahmen nicht betätigen.
     * Wenn exportEinnahmen oder exportAusgaben ausgewählt sind und man nun exportAlles auswählt, werden diese beiden deaktiviert.
     */
    public void checkCheckBoxesExport() {
        if (exportAlles.isSelected()) {
            exportAusgaben.setSelected(false);
            exportEinnahmen.setSelected(false);
            exportEinnahmen.setDisable(true);
            exportAusgaben.setDisable(true);
        } else {
            exportEinnahmen.setDisable(false);
            exportAusgaben.setDisable(false);
        }
    }

    /**
     * Wenn man Einnahme auswählt, wird Ausgabe deaktiviert.
     *
     * @param actionEvent → wird beim Drücken der Checkbox ausgeführt.
     */
    public void checkCheckBoxesDauerauftragE(ActionEvent actionEvent) {
        if (dauerauftragEinnahme.isSelected()) {
            dauerauftragAusgabe.setSelected(false);
        }
    }

    /**
     * Wenn man Ausgabe auswählt, wird Einnahme deaktiviert.
     *
     * @param actionEvent → wird beim Drücken der Checkbox ausgeführt.
     */
    public void checkCheckBoxesDauerauftragA(ActionEvent actionEvent) {
        if (dauerauftragAusgabe.isSelected()) {
            dauerauftragEinnahme.setSelected(false);
        }
    }

    /**
     * Beim Drücken des Knopfes öffnet sich ein Fenster, in dem man den Speicherort der PDFDatei festlegen kann.
     *
     * @param event → wird beim Drücken des Knopfes ausgeführt.
     */
    @FXML
    void exportierenWoBtnPressed(ActionEvent event) {
        Stage stage = (Stage) exportWo.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            System.out.println(selectedDirectory.getAbsolutePath());
            exportSpeicherort.setText(String.valueOf(selectedDirectory));
        }

    }

    /**
     * Beim Wechseln des Tabs werden alle Labels auf leer gesetzt.
     *
     * @param event → wird beim Drücken des Tabs ausgeführt.
     */
    public void clearLabels(Event event) {
        labelEinnahmen.setText(null);
        labelAusgaben.setText(null);
        labelDauerauftraege.setText(null);
        labelExport.setText(null);
    }

    /**
     * Beim Drücken des Reload-Buttons lädt es alle Anzeigen(Tableviews), sowie Kontodaten neu.
     *
     * @param actionEvent → wird beim Drücken des Knopfes ausgeführt.
     */
    public void ladeDatenNeu(ActionEvent actionEvent) {
        oblistAusgaben.clear();
        oblistDauerauftraege.clear();
        oblistEinnahmen.clear();
        ladeDatenAusgaben();
        ladeDatenEinnahmen();
        ladeDatenDauerauftrag();
    }

    /**
     * Wenn Täglich ausgewählt ist, setzt es den Text darunter auf diesen Wert.
     *
     * @param actionEvent → wird beim Drücken des Knopfes ausgeführt.
     */
    public void taeglich(ActionEvent actionEvent) {
        dauerauftragZeitspanneText.setText("Täglich");
    }

    /**
     * Wenn Wöchentlich ausgewählt ist, setzt es den Text darunter auf diesen Wert.
     *
     * @param actionEvent → wird beim Drücken des Knopfes ausgeführt.
     */
    public void woechentlich(ActionEvent actionEvent) {
        dauerauftragZeitspanneText.setText("Wöchentlich");
    }

    /**
     * Wenn Monatlich ausgewählt ist, setzt es den Text darunter auf diesen Wert.
     *
     * @param actionEvent → wird beim Drücken des Knopfes ausgeführt.
     */
    public void monatlich(ActionEvent actionEvent) {
        dauerauftragZeitspanneText.setText("Monatlich");
    }

    /**
     * Wenn Jährlich ausgewählt ist, setzt es den Text darunter auf diesen Wert.
     *
     * @param actionEvent → wird beim Drücken des Knopfes ausgeführt.
     */
    public void jaehrlich(ActionEvent actionEvent) {
        dauerauftragZeitspanneText.setText("Jährlich");
    }

}
