package finanzmanager;

import database.JavaPostgres;
import database.PasswordEncryption;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
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
    /**
     * Textfeld zur Darstellung der Kategorie einer Ausgabe
     */
    private TextField ausgabenKategorieText;

    @FXML
    /**
     * Menübutton zur Auswahl der Kategorien
     */
    private MenuButton menubarKategorieAusgaben;

    @FXML
    private TextField ausgabenBezeichnung;

    @FXML
    private ListView ausgabenUerbsichtListView;

    //----------------------------------------------------------------


    //-----------------Einnahmen Reiter--------------------------------
    @FXML
    private TextField einnahmenBetrag;

    @FXML
    private DatePicker einnahmenDate;
    @FXML
    private TextField einnahmenKategorieText;
    @FXML
    private MenuButton menubarKategorieEinnahmen;

    @FXML
    private TextField einnahmenBezeichnung;

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

    //----------------------------------------------------------------

    @FXML
    private Button quitBtn;


    //----------------------------------------------------------------

    //Next Window


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
    void ausgabeHinzufuegenBtn(ActionEvent event) {

    }


    //Zeigt die Ausgewählte Kategorie in dem Textfeld darunter an
    @FXML
    public void sonstiges(ActionEvent actionEvent) {
        ausgabenKategorieText.setText("Sonstiges");
    }

    @FXML
    public void sozialleben(ActionEvent actionEvent) {
        ausgabenKategorieText.setText("Sozialleben");
    }

    @FXML
    public void kultur(ActionEvent actionEvent) {
        ausgabenKategorieText.setText("Kultur");
    }

    @FXML
    public void beauty(ActionEvent actionEvent) {
        ausgabenKategorieText.setText("Beauty");
    }

    @FXML
    public void bekleidung(ActionEvent actionEvent) {
        ausgabenKategorieText.setText("Bekleidung");
    }

    @FXML
    public void auto(ActionEvent actionEvent) {
        ausgabenKategorieText.setText("Auto");
    }

    @FXML
    public void essen(ActionEvent actionEvent) {
        ausgabenKategorieText.setText("Essen");
    }
    //----------------------------------------------------------------


    //Zeigt die Ausgewählte Kategorie in dem Textfeld der Einnahmen darunter an
    public void essenEinnahmen(ActionEvent actionEvent) {
        einnahmenKategorieText.setText("Essen");
    }

    public void autoEinnahmen(ActionEvent actionEvent) {
        einnahmenKategorieText.setText("Auto");
    }

    public void bekleidungEinnahmen(ActionEvent actionEvent) {
        einnahmenKategorieText.setText("Bekleidung");
    }

    public void beautyEinnahmen(ActionEvent actionEvent) {
        einnahmenKategorieText.setText("Beauty");
    }

    public void kulturAusgaben(ActionEvent actionEvent) {
        einnahmenKategorieText.setText("Kultur");
    }

    public void soziallebenEinnahmen(ActionEvent actionEvent) {
        einnahmenKategorieText.setText("Sozialleben");
    }

    public void sonstigesEinnahmen(ActionEvent actionEvent) {
        einnahmenKategorieText.setText("Sonstiges");
    }

    public void kulturEinnahmen(ActionEvent actionEvent) {
        einnahmenKategorieText.setText("Kultur");
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
