package finanzmanager;

import database.GetPostgresData;
import database.JavaPostgres;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelclasses.Ausgaben;
import modelclasses.UserLogin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.JavaPostgres.databaseConnectionLink;

public class AusgabenController implements Initializable
{
    int id = UserLogin.id;

    @FXML
    private TableView<Ausgaben> ausgabenControllerTable;

    @FXML
    private TableColumn<Ausgaben, Float> ausgabenControllerBetrag;

    @FXML
    private TableColumn<Ausgaben, String> ausgabenControllerBezeichnung;

    @FXML
    private TableColumn<Ausgaben, Date> ausgabenControllerDatum;


    ObservableList<Ausgaben> observableList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        JavaPostgres javaPostgres = new JavaPostgres();
        javaPostgres.getConnection();


        try
        {
        Connection con = databaseConnectionLink;
        PreparedStatement pstAusgaben = con.prepareStatement("SELECT * FROM ausgaben WHERE user_ausgabenid=?");
        pstAusgaben.setInt(1, id);
        ResultSet rs = pstAusgaben.executeQuery("SELECT * FROM ausgaben WHERE user_ausgabenid=?");

        while (rs.next()) {
            observableList.add(
                    new Ausgaben(
                    rs.getFloat("ausgaben_betrag"),
                    rs.getString("usgaben_bezeichnung"),
                    rs.getDate("ausgaben_datum")
                    )
            );
        }



        }
        catch (SQLException exception)
        {
            Logger.getLogger(AusgabenController.class.getName()).log(Level.SEVERE, null ,exception);
        }

        ausgabenControllerBetrag.setCellValueFactory(new PropertyValueFactory<>("ausgaben_betrag"));
        ausgabenControllerBezeichnung.setCellValueFactory(new PropertyValueFactory<>("ausgaben_bezeichnung"));
        ausgabenControllerDatum.setCellValueFactory(new PropertyValueFactory<>("ausgaben_datum"));


        ausgabenControllerTable.setItems(observableList);

    }

















}
