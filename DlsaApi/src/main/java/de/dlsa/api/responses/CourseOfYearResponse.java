package de.dlsa.api.responses;

import de.dlsa.api.entities.CourseOfYear;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class CourseOfYearResponse {
    private byte[] file;
    private LocalDateTime timestamp;
    private String displayName;
    private String filename;
    private LocalDateTime dueDate;

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
