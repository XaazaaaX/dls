package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class ActionDto {
    @NotBlank(message = "Jahr ist erforderlich")
    @Pattern(regexp = "^\\d{1,4}$", message = "Jahr darf nur 4 Zahlen besitzen")
    private String year;
    @NotBlank(message = "Beschreibung ist erforderlich")
    private String description;
    @NotBlank(message = "Kontakt ist erforderlich")
    private String contactId;

    public String getYear() {
        return year;
    }

    public ActionDto setYear(String year) {
        this.year = year;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ActionDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getContactId() {
        return contactId;
    }

    public ActionDto setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }
}
