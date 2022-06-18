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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
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
    private TextField ausgabenBetrag;

    @FXML
    private DatePicker ausgabenDate;

    @FXML
    private TextField ausgabenKategorieText;

    @FXML
    private MenuButton menubarKategorieAusgaben;

    @FXML
    private TextField ausgabenBezeichnung;

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
    void btnOKClicked(ActionEvent event) throws IOException{

        if(!loginName.getText().isBlank() && !loginPassword.getText().isBlank())
        {
            System.out.println("Login...");
            validateUserLogin();
        }
        else
        {
            System.out.println("Enter login data");
            errorText.setText("Please type in your Username and Password");
        }
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
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

    public void setRegistrationData(ActionEvent actionEvent){

        System.out.println(registrationUserName.getText());
        System.out.println(registrationUserPassword.getText());

        if (Objects.equals(registrationName.getText(), ""))
        {
            regsuccsessfulllabel.setText("No Name!");
        }
        else if(Objects.equals(registrationUserName.getText(), "")) {
            regsuccsessfulllabel.setText("No Login Name!");
        }
        else if (Objects.equals(registrationUserPassword.getText(), "")) {
            regsuccsessfulllabel.setText("No Password!");
        } else {
            JavaPostgres.writeToDatabase(registrationName.getText(), registrationUserName.getText(), registrationUserPassword.getText());
            registrationName.clear();
            registrationUserName.clear();
            registrationUserPassword.clear();
        }


    }

    public void validateUserLogin()
    {
        PreparedStatement preparedStatement;
        ResultSet rs;

        try
        {
            JavaPostgres connectNow = new JavaPostgres();
            Connection conDb = connectNow.getConnection();
            preparedStatement = conDb.prepareStatement("SELECT password, passwordsalt FROM Userinfo WHERE username = ?");
            preparedStatement.setString(1, loginName.getText());
            rs = preparedStatement.executeQuery();

            Statement statement = conDb.createStatement();

            while (rs.next())
            {
                // User provided password to validate
                String providedPassword =  loginPassword.getText();

                // Encrypted and Base64 encoded password read from database
                String securePassword = rs.getString("password");

                // Salt value stored in database
                String salt = rs.getString("passwordsalt");

                boolean passwordMatch = PasswordEncryption.verifyUserPassword(providedPassword, securePassword, salt);

                if(passwordMatch)
                {
                    System.out.println("Login success");

                    errorText.setText("Success");
                    Stage stage = (Stage) loginOkBtn.getScene().getWindow();
                    stage.close();
                    Stage primaryStage = new Stage();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Actualview.fxml")));
                    primaryStage.setTitle("FINANZMANAGER - " + loginName.getText());
                    primaryStage.setScene(new Scene(root, 1082, 726));
                    //primaryStage.initStyle(StageStyle.TRANSPARENT);
                    primaryStage.setResizable(false);
                    primaryStage.show();

                }
                else
                {
                    System.out.println("Login failed");
                    errorText.setText("Please type in right Password");
                    loginPassword.clear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
