package finanzmanager;

import database.GetPostgresData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static finanzmanager.LoginController.getUserFullname;

public class Programm extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Programm.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void startactualview(/*Stage stage*/) throws IOException {
       /* Stage stage = (Stage) loginOkBtn.getScene().getWindow();
        stage.close(); */
        GetPostgresData.getEinnahmenDatabase();
        GetPostgresData.getDauerauftragDatabase();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(Programm.class.getResource("Actualview.fxml")));
        primaryStage.setTitle("FINANZMANAGER - "/* + loginName.getText() + " - " + getUserFullname()*/);
        //primaryStage.setScene(new Scene(root, 1082, 726));
        primaryStage.setScene(new Scene(root, 1082, 737));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}