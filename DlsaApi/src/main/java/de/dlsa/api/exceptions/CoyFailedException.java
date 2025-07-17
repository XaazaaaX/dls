package de.dlsa.api.exceptions;

/**
 * Benutzerdefinierte Ausnahme für Fehler, die beim Ausführen eines Jahreslaufs (Course of Year, kurz "CoY") auftreten.
 * Wird z. B. verwendet, wenn eine Datei fehlschlägt, eine Validierung nicht bestanden wird
 * oder eine Abbruchbedingung erreicht wird.
 *
 * Diese Exception signalisiert explizit einen fachlichen Fehler im Jahreslaufprozess.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class CoyFailedException extends Exception {

    /**
     * Erstellt eine neue Ausnahmeinstanz mit benutzerdefinierter Fehlermeldung.
     *
     * @param message Beschreibung des Fehlers
     */
    public CoyFailedException(String message) {
        super(message);
    }
}
