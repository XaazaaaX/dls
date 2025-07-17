package de.dlsa.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Repräsentiert einen Jahreslauf (z. B. ein Abschlussbericht oder Abrechnungslauf für ein Vereinsjahr).
 * Enthält Metadaten zur Datei, Erfassungszeitpunkt und ein Stichtagsdatum.
 * Die Datei selbst wird als Byte-Array direkt in der Datenbank gespeichert.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "Jahreslaeufe")
public class CourseOfYear extends BaseEntity {

    /**
     * Binärinhalt der Datei.
     */
    @Column(name = "datei")
    private byte[] file;

    /**
     * Zeitstempel der Erstellung oder Speicherung dieses Jahreslaufs.
     */
    private LocalDateTime timestamp;

    /**
     * Anzeigename für die Datei
     */
    @Column(name = "anzeigename")
    private String displayName;

    /**
     * Ursprünglicher Dateiname beim Hochladen
     */
    @Column(name = "dateiname")
    private String filename;

    /**
     * Stichtagsdatum, auf das sich der Jahreslauf bezieht (z. B. 31.12.2024).
     */
    @Column(name = "stichtagsdatum")
    private LocalDateTime dueDate;

    // ===== Getter & Setter (Fluent API) =====

    public byte[] getFile() {
        return file;
    }

    public CourseOfYear setFile(byte[] file) {
        this.file = file;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public CourseOfYear setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public CourseOfYear setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public CourseOfYear setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public CourseOfYear setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }
}
