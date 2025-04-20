package de.dlsa.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "Jahreslaeufe")
public class CourseOfYear extends BaseEntity{

    @Column(name = "datei")
    private byte[] file;
    private Instant timestamp;
    @Column(name = "anzeigename")
    private String displayName;
    @Column(name = "dateiname")
    private String filename;
    @Column(name = "stichtagsdatum")
    private Instant dueDate;

    /*
    public CourseOfYear() {

    }

    public CourseOfYear(byte[] file, Instant timestamp, String displayName,
                        String filename, Instant dueDate) {
        this.file = file;
        this.timestamp = timestamp;
        this.displayName = displayName;
        this.filename = filename;
        this.dueDate = dueDate;
    }

     */

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

}
