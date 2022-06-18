package database;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;





public class PasswordEncryption {

    //Generiert zufälligen Wert für salt
    private static final Random RANDOM = new SecureRandom();

    //Nutzt den Zahlenbereich und Alphabet als Vorlage für das zufällig-generierte salt
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    //Legt die Anzahl der hashing-Vorgänge fest, bis das gesicherte Passwort in die Datenbank gespeichert wird
    private static final int ITERATIONS = 10000;

    //Beschreibt die Hashlänge in Bits
    private static final int KEY_LENGTH = 256;


    /**
     * Funktion getSalt(int length) zur Erstellung des salt, basierend auf die final-Variablen RANDOM und ALPHABET
     *
     * @return  returnValue
     */

    public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }


    /**
     * Funktion hash(char[] password, byte[] salt) greift auf die final-Variablen ITERATIONS und KEY_LENGTH zu
     * Mittels des PBE-Schlüsselgenerator wird das Passwort mit PBKDF2-generierten hashes kodiert
     */

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * generateSecurePassword(String password, String salt) generiert in Kombination mit dem kodierten Passwort
     * und salt das verschlüsselte Passwort
     * Es wird dabei erst mit der hash-Funktion erfasst und per Base64 verschlüsselt
     *
     * @param password -    Erster Wert für Userpasswort
     * @param salt -    Zweiter Wert zur Verschlüsselung des Userpasswort
     *
     *
     */

    public static String generateSecurePassword(String password, String salt) {
        String returnValue;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    public static boolean verifyUserPassword(String providedPassword,
                                             String securedPassword, String salt) {
        boolean returnValue;

        // Generiert neues Sicherheitspasswort mit zugeteilten salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        // Vergleicht die Passwörter zur Validierung der Logindaten
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }
}