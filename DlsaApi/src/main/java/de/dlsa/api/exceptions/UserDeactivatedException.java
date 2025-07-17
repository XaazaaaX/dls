package de.dlsa.api.exceptions;

/**
 * Ausnahme, die geworfen wird, wenn ein Benutzer deaktiviert ist (z. B. durch Admin oder System).
 * Diese Exception wird z. B. im Authentifizierungsprozess verwendet,
 * um den Zugriff für inaktive Benutzer zu verweigern.
 *
 * Sie wird vom {@link de.dlsa.api.exceptions.GlobalExceptionHandler} abgefangen
 * und als HTTP 403 zurückgegeben.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class UserDeactivatedException extends Exception {

    /**
     * Erstellt eine neue Ausnahmeinstanz mit Standardmeldung.
     */
    public UserDeactivatedException() {
        super("Benutzer ist deaktiviert.");
    }
}