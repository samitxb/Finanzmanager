package modelclasses;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class NurNummern {

    /**
     * Funktion lässt keine Buchstaben zu, diese werden automatisch ersetzt. Double Werte sind erlaubt.
     * @param textfeld -> String, der betrachtet werden soll.
     */
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


}
