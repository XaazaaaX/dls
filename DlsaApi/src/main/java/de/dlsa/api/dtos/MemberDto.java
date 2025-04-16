package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.Instant;
import java.util.Collection;

public class MemberDto {
    @NotBlank(message = "Nachname ist erforderlich")
    private String surname;
    @NotBlank(message = "Vorname ist erforderlich")
    private String forename;
    @NotBlank(message = "Mitgliedsnummer ist erforderlich")
    private String memberId;
    @NotNull(message = "Eintrittsdatum ist erforderlich")
    private Instant entryDate;
    private Instant  leavingDate;
    private Boolean active;
    @NotNull(message = "Geburtstag ist erforderlich")
    private Instant  birthdate;
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

    public Instant  getEntryDate() {
        return entryDate;
    }

    public MemberDto setEntryDate(Instant  entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public Instant  getLeavingDate() {
        return leavingDate;
    }

    public MemberDto setLeavingDate(Instant  leavingDate) {
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

    public Instant  getBirthdate() {
        return birthdate;
    }

    public MemberDto setBirthdate(Instant  birthdate) {
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
