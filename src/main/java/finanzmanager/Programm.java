/*
 * Klasse führt den Start des Finanzmanagers aus
 */
package finanzmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasse Programm zur Ausführung des Finanzmanagers
 *
 * @author Max Weichselgartner
 * @version 1.0.0
 */
public class Programm extends Application {

    public static void main(String[] args) {
        launch();
    }

    /**
     * Funktion lädt den Loginscreen
     *
     * @param stage Fenster für die Scene
     * @throws IOException → wirft einen Fehler
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Programm.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
