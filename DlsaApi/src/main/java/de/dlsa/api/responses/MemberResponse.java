package de.dlsa.api.responses;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Repräsentiert die Antwortstruktur für ein Mitglied.
 * Enthält persönliche Daten sowie zugeordnete Gruppen und Sparten.
 *
 * @version 05/2025
 */
public class MemberResponse {

    /** Eindeutige Identifikationsnummer des Mitglieds. */
    private Long id;

    /** Nachname des Mitglieds. */
    private String surname;

    /** Vorname des Mitglieds. */
    private String forename;

    /** Mitgliedsnummer. */
    private String memberId;

    /** Eintrittsdatum des Mitglieds. */
    private LocalDateTime entryDate;

    /** Austrittsdatum des Mitglieds. */
    private LocalDateTime leavingDate;

    /** Aktivitätsstatus (true = aktiv, false = passiv). */
    private Boolean active;

    /** Geburtsdatum des Mitglieds. */
    private LocalDateTime birthdate;

    /** Kennzeichnung, ob das Mitglied zur DLS-Abrechnung zählt. */
    private Boolean aikz = true;

    /** Zugeordnete Gruppen des Mitglieds. */
    private Collection<GroupResponse> groups = new ArrayList<>();

    /** Zugeordnete Sparten des Mitglieds. */
    private Collection<CategoryResponse> categories = new ArrayList<>();

    // --- Getter & Fluent Setter ---

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
}
