package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class ActionDto {
    @NotBlank(message = "Jahr ist erforderlich")
    @Pattern(regexp = "^\\d{1,4}$", message = "Jahr darf nur 4 Zahlen besitzen")
    private String year;
    @NotBlank(message = "Beschreibung ist erforderlich")
    private String description;
    @NotNull(message = "Kontakt ist erforderlich")
    private Long contactId;

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

    public Long getContactId() {
        return contactId;
    }

    public ActionDto setContactId(Long contactId) {
        this.contactId = contactId;
        return this;
    }
}
