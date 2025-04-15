package de.dlsa.api.responses;

import de.dlsa.api.dtos.MemberDto;
import de.dlsa.api.entities.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MemberResponse {
    private Long id;
    private String surname;
    private String forename;
    private String memberId;
    private LocalDateTime entryDate;
    private LocalDateTime leavingDate;
    private Boolean active;
    private LocalDateTime birthdate;
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

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public MemberResponse setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public LocalDateTime getLeavingDate() {
        return leavingDate;
    }

    public MemberResponse setLeavingDate(LocalDateTime leavingDate) {
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

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public MemberResponse setBirthdate(LocalDateTime birthdate) {
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
