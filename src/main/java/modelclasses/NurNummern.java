package modelclasses;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class NurNummern {


        public static void numericOnly(final TextField field) {
            field.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(
                        ObservableValue<? extends String> observable,
                        String oldValue, String newValue) {
                    if (!newValue.matches("\\d+\\.\\d+")) {
                        field.setText(newValue.replaceAll("[^\\d+\\.\\d+]", ""));
                    }
                }
            });
        }

        public static boolean isNumeric(String eingabe){
           /* String str = eingabe;
            //boolean isNumeric = true;
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }*/
            return true;
        }

    UnaryOperator<TextFormatter.Change> numberValidationFormatter = change -> {
        if(change.getText().matches("\\\\d+\\\\.\\\\d+")){
            return change; //if change is a number
        } else {
            change.setText(""); //else make no change
            change.setRange(    //don't remove any selected text either.
                    change.getRangeStart(),
                    change.getRangeStart()
            );
            return change;
        }
    };

}
