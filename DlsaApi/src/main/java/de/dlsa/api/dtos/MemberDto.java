package de.dlsa.api.dtos;

import java.util.Collection;
import java.util.Date;

public class MemberDto {
    private String surname;
    private String forename;
    private String memberId;
    private Date entryDate;
    private Date leavingDate;
    private Boolean active;
    private Date birthdate;
    private Boolean aikz = true;
    private Collection<Long> groupIds;
    private Collection<Long> categorieIds;

    public String getSurname() {
        return surname;
    }

    public MemberDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getForename() {
        return forename;
    }

    public MemberDto setForename(String forename) {
        this.forename = forename;
        return this;
    }

    public String getMemberId() {
        return memberId;
    }

    public MemberDto setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public MemberDto setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public Date getLeavingDate() {
        return leavingDate;
    }

    public MemberDto setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public MemberDto setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Collection<Long> getGroupIds() {
        return groupIds;
    }

    public MemberDto setGroupIds(Collection<Long> groupIds) {
        this.groupIds = groupIds;
        return this;
    }

    public Collection<Long> getCategorieIds() {
        return categorieIds;
    }

    public MemberDto setCategorieIds(Collection<Long> categorieIds) {
        this.categorieIds = categorieIds;
        return this;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public MemberDto setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public Boolean getAikz() {
        return aikz;
    }

    public MemberDto setAikz(Boolean aikz) {
        this.aikz = aikz;
        return this;
    }
}
