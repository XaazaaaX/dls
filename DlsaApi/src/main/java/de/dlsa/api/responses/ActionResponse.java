package de.dlsa.api.responses;

public class ActionResponse {
    private Long id;

    private String year;

    private String description;

    private MemberResponse contact;

    public Long getId() {
        return id;
    }

    public ActionResponse setId(Long id){
        this.id = id;
        return this;
    }

    public String getYear() {
        return year;
    }

    public ActionResponse setYear(String year) {
        this.year = year;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ActionResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public MemberResponse getContact() {
        return contact;
    }

    public ActionResponse setContact(MemberResponse contact) {
        this.contact = contact;
        return this;
    }
}
