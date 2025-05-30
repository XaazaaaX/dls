package de.dlsa.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Jahreslaeufe")
public class CourseOfYear extends BaseEntity{

    @Column(name = "datei")
    private byte[] file;
    private LocalDateTime timestamp;
    @Column(name = "anzeigename")
    private String displayName;
    @Column(name = "dateiname")
    private String filename;
    @Column(name = "stichtagsdatum")
    private LocalDateTime dueDate;

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
