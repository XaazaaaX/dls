package de.dlsa.api.dtos;

import de.dlsa.api.responses.MemberResponse;

public class ActionDto {
    private String year;

    private String description;

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
