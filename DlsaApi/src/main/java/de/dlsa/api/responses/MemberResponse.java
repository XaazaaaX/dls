package de.dlsa.api.responses;

public class MemberResponse {
    private String memberId;
    private String surname;
    private String forename;


    public String getMemberId() {
        return memberId;
    }

    public MemberResponse setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public MemberResponse setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getForename() {
        return forename;
    }

    public MemberResponse setForename(String forename) {
        this.forename = forename;
        return this;
    }
}
