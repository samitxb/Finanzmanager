/*
 * Diese Klasse dient zur Überprüfung von Zahlen
 */

package modelclasses;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Klasse NurNummer wird benötigt für die überprüfung und Ersetzung von ausschließlich numerischen Textfeldern.
 *
 * @author Max Weichselgartner
 * @version 1.0
 */
public class NurNummern {

    /**
     * Funktion lässt keine Buchstaben zu, diese werden automatisch ersetzt. Double Werte sind erlaubt.
     *
     * @param textfeld -> String, der betrachtet werden soll.
     */
    public static void numericOnly(final TextField textfeld) {
        textfeld.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+\\.\\d+")) {
                textfeld.setText(newValue.replaceAll("[^\\d+.]", ""));
            }
        });
    }

    /**
     * Funktion überprüft, ob der Wert ein Double ist
     *
     * @param data ist der zu überprüfende String
     * @return true, falls der Wert ein Double ist
     */
    public static boolean isDouble(String data) {
        try {
            Double.parseDouble(data);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Kein Double");
            return false;
        }
    }

//Alte Version von numericOnly
/*
    public static void numericOnly(final TextField textfeld) {
        textfeld.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d+\\.\\d+")) {
                    textfeld.setText(newValue.replaceAll("[^\\d+\\.\\d+]", ""));
                }
            }
        });
    }
*/

}
