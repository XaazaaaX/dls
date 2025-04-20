package de.dlsa.api.responses;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

public class MemberResponse {
    private Long id;
    private String surname;
    private String forename;
    private String memberId;
    private Instant entryDate;
    private Instant leavingDate;
    private Boolean active;
    private Instant birthdate;
    private Boolean aikz = true;
    private Collection<GroupResponse> groups = new ArrayList<GroupResponse>();
    private Collection<CategoryResponse> categories = new ArrayList<CategoryResponse>();

    public Long getId() {
        return id;
    }

    public MemberResponse setId(Long id){
        this.id = id;
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

    public String getMemberId() {
        return memberId;
    }

    public MemberResponse setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public Instant getEntryDate() {
        return entryDate;
    }

    public MemberResponse setEntryDate(Instant entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public Instant getLeavingDate() {
        return leavingDate;
    }

    public MemberResponse setLeavingDate(Instant leavingDate) {
        this.leavingDate = leavingDate;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public MemberResponse setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Collection<GroupResponse> getGroups() {
        return groups;
    }

    public MemberResponse setGroups(Collection<GroupResponse> groups) {
        this.groups = groups;
        return this;
    }

    public Collection<CategoryResponse> getCategories() {
        return categories;
    }

    public MemberResponse setCategories(Collection<CategoryResponse> categories) {
        this.categories = categories;
        return this;
    }

    public Instant getBirthdate() {
        return birthdate;
    }

    public MemberResponse setBirthdate(Instant birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public Boolean getAikz() {
        return aikz;
    }

    public MemberResponse setAikz(Boolean aikz) {
        this.aikz = aikz;
        return this;
    }
}
