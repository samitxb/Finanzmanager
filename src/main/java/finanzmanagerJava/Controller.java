package finanzmanagerJava;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;


public class Controller {

    //--------------Login Register----------------------------------
    @FXML
    private TextField loginName;
    @FXML
    private PasswordField loginPassword;


    @FXML
    private Label errorText;


    @FXML
    private Button loginOkBtn;
    @FXML
    private Button quitBtn;
    //---------------------------------------------------------------


    //-------------Registration---------------------------------------
    @FXML
    private TextField registrationName;
    @FXML
    private TextField registrationUserName;
    @FXML
    private TextField registrationUserPassword;
    @FXML
    private Label regsuccsessfulllabel;
    //----------------------------------------------------------------


    //-----------------Ausgaben Reiter--------------------------------
    @FXML
    private TextField ausgaben_betrag;

    @FXML
    private DatePicker ausgaben_date;

    @FXML
    private TextField ausgaben_kategorie_text;

    @FXML
    private MenuButton menubar_kategorie_ausgaben;

    //----------------------------------------------------------------


    //-----------------Einnahmen Reiter--------------------------------
    @FXML
    private TextField einnahmen_betrag;

    @FXML
    private DatePicker einnahmen_date;
    @FXML
    private TextField einnahmen_kategorie_text;
    @FXML
    private MenuButton menubar_kategorie_einnahmen;

    //----------------------------------------------------------------

    //Next Window
    @FXML
    void btnGOClicked(ActionEvent event) throws IOException {
        errorText.setText("Please type in your Username and Password");
        Stage stage = (Stage) loginOkBtn.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Actualview.fxml")));
        primaryStage.setTitle("FINANZMANAGER");
        primaryStage.setScene(new Scene(root, 1082, 726));
        primaryStage.show();
    }


    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void quitApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void setRegistrationData(ActionEvent actionEvent) {
        System.out.println(registrationUserName.getText());
        System.out.println(registrationUserPassword.getText());


        if (Objects.equals(registrationUserName.getText(), "")) {
            regsuccsessfulllabel.setText("No Login Name!");
        } else if (Objects.equals(registrationUserPassword.getText(), "")) {
            regsuccsessfulllabel.setText("No Password!");
        } else regsuccsessfulllabel.setText("Registered Successfully!!");

        JavaPostgres.writeToDatabase(registrationName.getText(), registrationUserName.getText(), registrationUserPassword.getText());

    }


    //Zeigt die Ausgewählte Kategorie in dem Textfeld darunter an
    @FXML
    public void sonstiges(ActionEvent actionEvent) {
        ausgaben_kategorie_text.setText("Sonstiges");
    }

    @FXML
    public void sozialleben(ActionEvent actionEvent) {
        ausgaben_kategorie_text.setText("Sozialleben");
    }

    @FXML
    public void kultur(ActionEvent actionEvent) {
        ausgaben_kategorie_text.setText("Kultur");
    }

    @FXML
    public void beauty(ActionEvent actionEvent) {
        ausgaben_kategorie_text.setText("Beauty");
    }

    @FXML
    public void bekleidung(ActionEvent actionEvent) {
        ausgaben_kategorie_text.setText("Bekleidung");
    }

    @FXML
    public void auto(ActionEvent actionEvent) {
        ausgaben_kategorie_text.setText("Auto");
    }

    @FXML
    public void essen(ActionEvent actionEvent) {
        ausgaben_kategorie_text.setText("Essen");
    }
    //----------------------------------------------------------------


    //Zeigt die Ausgewählte Kategorie in dem Textfeld der Einnahmen darunter an
    public void essen_einnahmen(ActionEvent actionEvent) {
        einnahmen_kategorie_text.setText("Essen");
    }

    public void auto_einnahmen(ActionEvent actionEvent) {
        einnahmen_kategorie_text.setText("Auto");
    }

    public void bekleidung_einnahmen(ActionEvent actionEvent) {
        einnahmen_kategorie_text.setText("Bekleidung");
    }

    public void beauty_einnahmen(ActionEvent actionEvent) {
        einnahmen_kategorie_text.setText("Beauty");
    }

    public void kultur_ausgaben(ActionEvent actionEvent) {
        einnahmen_kategorie_text.setText("Kultur");
    }

    public void sozialleben_einnahmen(ActionEvent actionEvent) {
        einnahmen_kategorie_text.setText("Sozialleben");
    }

    public void sonstiges_einnahmen(ActionEvent actionEvent) {
        einnahmen_kategorie_text.setText("Sonstiges");
    }

    //----------------------------------------------------------------
}