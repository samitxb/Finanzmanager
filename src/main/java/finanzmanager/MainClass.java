package finanzmanager;


/**
 * Klasse MainClass zur Erstellung der .jar Datei
 * Es wird eine zusätzliche main ausgeführt, da in der Klasse Programm extends Application zu Fehler
 * bei der Erstellung der .jar Datei führt
 *
 * @author Michael  Irlmeier
 * @version 1.0.0
 */

public class MainClass {
    public static void main(String[] args) {
        Programm.main(args);
    }
}
