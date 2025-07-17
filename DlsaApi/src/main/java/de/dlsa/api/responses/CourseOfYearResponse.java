package de.dlsa.api.responses;

import java.time.LocalDateTime;

/**
 * Repräsentiert die Antwortstruktur für einen Jahreslauf.
 * Wird typischerweise vom Backend an das Frontend gesendet.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class CourseOfYearResponse {

    /**
     * Die eigentliche Datei im Binärformat.
     */
    private byte[] file;

    /**
     * Zeitpunkt der Erstellung bzw. Verarbeitung.
     */
    private LocalDateTime timestamp;

    /**
     * Anzeigename für den Benutzer (z. B. „Abrechnung 2024“).
     */
    private String displayName;

    /**
     * Der technische Dateiname, unter dem das Dokument gespeichert ist.
     */
    private String filename;

    /**
     * Das Stichtagsdatum, z. B. Ende des Abrechnungszeitraums.
     */
    private LocalDateTime dueDate;

    // --- Getter & Fluent Setter ---

    public byte[] getFile() {
        return file;
    }

    public CourseOfYearResponse setFile(byte[] file) {
        this.file = file;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public CourseOfYearResponse setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public CourseOfYearResponse setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public CourseOfYearResponse setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public CourseOfYearResponse setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }
}
