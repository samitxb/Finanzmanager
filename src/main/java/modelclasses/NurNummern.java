package modelclasses;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NurNummern {

/*
        public static void numericOnly(final TextField field) {
            field.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(
                        ObservableValue<? extends String> observable,
                        String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        field.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        }
*/
        public static boolean isNumeric(String eingabe){
            String str = eingabe;
            //boolean isNumeric = true;
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

}
