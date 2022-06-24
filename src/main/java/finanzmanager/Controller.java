package finanzmanager;

import database.GetPostgresData;
import database.JavaPostgres;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelclasses.NurNummern;
import modelclasses.UserLogin;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.JavaPostgres.databaseConnectionLink;


public class Controller implements Initializable {


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

    //------------------------------------------------------------------

    @FXML
    private Pagination ausgabenPagination;

   /* private final static int dataSize = 100;

    private final TableView<Sample> table = createTable();

    private final List<Sample> data = createData();

    private final static int rowsPerPage = 10;
*/
    //----------------------------------------------------------------
    @FXML
    private TableView<Ausgaben> ausgabenView;
    @FXML
    private TableColumn<Ausgaben, Float> ausgabenListBetrag;

    @FXML
    private TableColumn<Ausgaben, String> ausgabenListBezeichnung;

    @FXML
    private TableColumn<Ausgaben, String> ausgabenListDatum;

    ObservableList<Ausgaben> oblistausgaben =FXCollections.observableArrayList();
    int id = UserLogin.id;

    //----------------------------------------------------------------

    @FXML
    private TableColumn<Dauerauftraege, Float> dauerauftraegeListBetrag;

    @FXML
    private TableColumn<Dauerauftraege, String> dauerauftraegeListBezeichnung;

    @FXML
    private TableColumn<Dauerauftraege,String > dauerauftraegeListDatum;

    @FXML
    private TableView<Dauerauftraege> dauerauftraegeView;
    @FXML
    private TableColumn<Dauerauftraege, String> dauerauftraegeListDauer;

    ObservableList<Dauerauftraege> oblistdauerauftraege =FXCollections.observableArrayList();

    //----------------------------------------------------------------


    @FXML
    private TableColumn<Einnahmen, Float> einnahmenListBetrag;

    @FXML
    private TableColumn<Einnahmen, String> einnahmenListBezeichnung;

    @FXML
    private TableColumn<Einnahmen, String> einnahmenListDatum;

    @FXML
    private TableView<Einnahmen> einnahmenView;

    ObservableList<Einnahmen> oblisteinnahmen =FXCollections.observableArrayList();

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
/*
    private TableView<Sample> createTable() {

        TableView<Sample> table = new TableView<>();

        TableColumn<Sample, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(param -> param.getValue().id);
        idColumn.setPrefWidth(100);

        TableColumn<Sample, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> param.getValue().foo);
        nameColumn.setPrefWidth(70);

        table.getColumns().addAll(idColumn, nameColumn);
        return table;
    }

    //this method used to fill data in tableview
    private List<Sample> createData() {

        List<Sample> data = new ArrayList<>(dataSize);

        for (int i = 1; i <= dataSize; i++) {
            data.add(new Sample(i, "foo " + i, "bar " + i));
        }

        return data;
    }
    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        table.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
        return table;
    }

    //@Override
    public void initialize(URL url, ResourceBundle rb) {

        ausgabenPagination.setPageFactory(this::createPage);

    }

    //static class for data model
    public static class Sample {

        private final ObservableValue<Integer> id;
        private final SimpleStringProperty foo;
        private final SimpleStringProperty bar;

        private Sample(int id, String foo, String bar) {
            this.id = new SimpleObjectProperty<>(id);
            this.foo = new SimpleStringProperty(foo);
            this.bar = new SimpleStringProperty(bar);
        }
    }
*/
    //--------------------------------------------------------------------------

/*
    private ObservableList<Ausgaben> getUserList() {
        Ausgaben ausgabe1 = new Ausgaben(10,"hammer","10-5-2022");
        Ausgaben ausgabe2 = new Ausgaben(100,"dildo","10-5-2022");
        ObservableList<Ausgaben> list = FXCollections.observableArrayList(ausgabe1, ausgabe2);
        return list;
    }
*/
        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            JavaPostgres javaPostgres = new JavaPostgres();
            javaPostgres.getConnection();

            try
            {
                Connection con = databaseConnectionLink;
                PreparedStatement pstAusgaben = con.prepareStatement("SELECT * FROM ausgaben WHERE user_ausgabenid=?");
                pstAusgaben.setInt(1, id);
                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM ausgaben ");
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    oblistausgaben.add(
                            new Ausgaben(
                                    rs.getFloat("ausgaben_betrag"),
                                    rs.getString("ausgaben_bezeichnung"),
                                    rs.getString("ausgaben_datum")
                            )
                    );
                }
            }
            catch (SQLException exception)
            {
                Logger.getLogger(Ausgaben.class.getName()).log(Level.SEVERE, null ,exception);
            }

            ausgabenListDatum.setCellValueFactory(new PropertyValueFactory<Ausgaben, String>("ausgabenListDatum"));
            ausgabenListBezeichnung.setCellValueFactory(new PropertyValueFactory<Ausgaben, String>("ausgabenListBezeichnung"));
            ausgabenListBetrag.setCellValueFactory(new PropertyValueFactory<Ausgaben, Float>("ausgabenListBetrag"));


            ausgabenView.setItems(oblistausgaben);

    //--------------------------------------------------------------------------------------------------------------------------------------------
            try
            {
                Connection con = databaseConnectionLink;
                PreparedStatement pstAusgaben = con.prepareStatement("SELECT * FROM einnahmen WHERE user_ausgabenid=?");
                pstAusgaben.setInt(1, id);
                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM einnahmen ");
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    oblisteinnahmen.add(
                            new Einnahmen(
                                    rs.getString("einnahmen_bezeichnung"),
                                    rs.getFloat("einnahmen_betrag"),
                                    rs.getString("einnahmen_datum")
                            )
                    );
                }
            }
            catch (SQLException exception)
            {
                Logger.getLogger(Einnahmen.class.getName()).log(Level.SEVERE, null ,exception);
            }
            einnahmenListDatum.setCellValueFactory(new PropertyValueFactory<Einnahmen, String>("einnahmenListDatum"));
            einnahmenListBezeichnung.setCellValueFactory(new PropertyValueFactory<Einnahmen, String>("einnahmenListBezeichnung"));
            einnahmenListBetrag.setCellValueFactory(new PropertyValueFactory<Einnahmen, Float>("einnahmenListBetrag"));


            einnahmenView.setItems(oblisteinnahmen);

            //--------------------------------------------------------------------------------------------------------------------------------------------

            try
            {
                Connection con = databaseConnectionLink;
                PreparedStatement pstAusgaben = con.prepareStatement("SELECT * FROM dauerauftrag WHERE user_ausgabenid=?");
                pstAusgaben.setInt(1, id);
                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM dauerauftrag ");
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    oblistdauerauftraege.add(
                            new Dauerauftraege(
                                    rs.getString("dauerauftrag_bezeichnung"),
                                    rs.getFloat("dauerauftrag_betrag"),
                                    rs.getString("dauerauftrag_datum"),
                                    rs.getString("dauerauftrag_zeitraum")
                            )
                    );
                }
            }
            catch (SQLException exception)
            {
                Logger.getLogger(Einnahmen.class.getName()).log(Level.SEVERE, null ,exception);
            }
            dauerauftraegeListDatum.setCellValueFactory(new PropertyValueFactory<Dauerauftraege, String>("dauerauftraegeListDatum"));
            dauerauftraegeListBezeichnung.setCellValueFactory(new PropertyValueFactory<Dauerauftraege, String>("dauerauftraegeListBezeichnung"));
            dauerauftraegeListBetrag.setCellValueFactory(new PropertyValueFactory<Dauerauftraege, Float>("dauerauftraegeListBetrag"));
            dauerauftraegeListDauer.setCellValueFactory(new PropertyValueFactory<Dauerauftraege, String >("dauerauftraegeListDauer"));

            dauerauftraegeView.setItems(oblistdauerauftraege);

    }
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

        labelAusgaben.setText(null);


        System.out.println(ausgabenBetrag.getText());
        System.out.println(ausgabenBezeichnung.getText());


        LocalDate localDate = ausgabenDate.getValue();
        System.out.println(localDate);

        /*LocalDate isoDate = ausgabenDate.getValue();
        ChronoLocalDate chronoDate = ((isoDate != null) ? ausgabenDate.getChronology().date(isoDate) : null);
        System.err.println("Selected date: " + chronoDate);*/

        boolean numerisch;

        if (Objects.equals(ausgabenBetrag.getText(), "")) {
            labelAusgaben.setText("Kein Betrag!");
        } else if (Objects.equals(ausgabenBezeichnung.getText(), "")) {
            labelAusgaben.setText("Keine Bezeichnung !");
        } else if (Objects.equals(localDate, null)) {
            labelAusgaben.setText("Kein Datum!");
        } else {
            numerisch = NurNummern.isNumeric(ausgabenBetrag.getText());
            if (numerisch) {
                labelAusgaben.setText("Gespeichert!");

                JavaPostgres.writeToDatabaseAusgaben(Float.valueOf(ausgabenBetrag.getText()), ausgabenBezeichnung.getText(), Date.valueOf(localDate));
                GetPostgresData.getAusgabenDatabase();

                ausgabenBetrag.clear();
                ausgabenBezeichnung.clear();
                ausgabenDate.setValue(null);
            } else labelAusgaben.setText("Keine Zahl!");
        }

    }


    /* Für Einnahmen alles */
    @FXML
    void einnahmeHinzufuegenBtn(ActionEvent event) throws SQLException {

        labelEinnahmen.setText(null);

        System.out.println(einnahmenBetrag.getText());
        System.out.println(einnahmenBezeichnung.getText());

        LocalDate localDate = einnahmenDate.getValue();
        System.out.println(localDate);

        boolean numerisch;

        if (Objects.equals(einnahmenBetrag.getText(), "")) {
            labelEinnahmen.setText("Kein Betrag!");
        } else if (Objects.equals(einnahmenBezeichnung.getText(), "")) {
            labelEinnahmen.setText("Keine Bezeichnung!");
        } else if (Objects.equals(localDate, null)) {
            labelEinnahmen.setText("Kein Datum!");
        } else {

            numerisch = NurNummern.isNumeric(einnahmenBetrag.getText());
            if (numerisch) {
                labelEinnahmen.setText("Gespeichert!");

                JavaPostgres.writeToDatabaseEinnahmen(Float.valueOf(einnahmenBetrag.getText()), einnahmenBezeichnung.getText(), Date.valueOf(localDate));
                GetPostgresData.getEinnahmenDatabase();

                einnahmenBetrag.clear();
                einnahmenBezeichnung.clear();
                einnahmenDate.setValue(null);
            } else labelEinnahmen.setText("Keine Zahl!");

        }
    }

    @FXML
    public void dauerauftragHinzufuegenBtn(ActionEvent actionEvent) throws SQLException {

        labelDauerauftraege.setText(null);

        System.out.println(dauerauftragBetrag.getText());
        System.out.println(dauerauftragBezeichnung.getText());
        System.out.println(dauerauftragZeitspanneText.getText());

        LocalDate localDate = dauerauftragDate.getValue();
        System.out.println(localDate);

        boolean numerisch;

        if (Objects.equals(dauerauftragBetrag.getText(), "")) {
            labelDauerauftraege.setText("Kein Betrag!");
        } else if (Objects.equals(dauerauftragBezeichnung.getText(), "")) {
            labelDauerauftraege.setText("Keine Bezeichnung!");
        } else if (Objects.equals(localDate, null)) {
            labelDauerauftraege.setText("Kein Datum!");
        } else if (Objects.equals(menubarZeitspanneDauerauftrag.getText(), "")) {
            labelDauerauftraege.setText("Keine Zeitspanne!");
        } else {

            numerisch = NurNummern.isNumeric(dauerauftragBetrag.getText());
            if (numerisch) {
                labelDauerauftraege.setText("Gespeichert!");

                JavaPostgres.writeToDatabaseDauerauftrag(Float.valueOf(dauerauftragBetrag.getText()), dauerauftragBezeichnung.getText(), Date.valueOf(localDate), dauerauftragZeitspanneText.getText());
                GetPostgresData.getDauerauftragDatabase();


                dauerauftragBetrag.clear();
                dauerauftragBezeichnung.clear();
                einnahmenDate.setValue(null);
                dauerauftragZeitspanneText.clear();

            } else labelDauerauftraege.setText("Keine Zahl!");
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
