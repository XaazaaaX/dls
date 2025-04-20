package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Collection;

public class MemberCreateDto {
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

    public MemberCreateDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getForename() {
        return forename;
    }

    public MemberCreateDto setForename(String forename) {
        this.forename = forename;
        return this;
    }

    public String getMemberId() {
        return memberId;
    }

    public MemberCreateDto setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public Instant  getEntryDate() {
        return entryDate;
    }

    public MemberCreateDto setEntryDate(Instant  entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public Instant  getLeavingDate() {
        return leavingDate;
    }

    public MemberCreateDto setLeavingDate(Instant  leavingDate) {
        this.leavingDate = leavingDate;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public MemberCreateDto setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Collection<Long> getGroupIds() {
        return groupIds;
    }

    public MemberCreateDto setGroupIds(Collection<Long> groupIds) {
        this.groupIds = groupIds;
        return this;
    }

    public Collection<Long> getCategorieIds() {
        return categorieIds;
    }

    public MemberCreateDto setCategorieIds(Collection<Long> categorieIds) {
        this.categorieIds = categorieIds;
        return this;
    }

    public Instant  getBirthdate() {
        return birthdate;
    }

    public MemberCreateDto setBirthdate(Instant  birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public Boolean getAikz() {
        return aikz;
    }

    public MemberCreateDto setAikz(Boolean aikz) {
        this.aikz = aikz;
        return this;
    }
}
