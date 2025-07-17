package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Datenübertragungsobjekt zur Erstellung eines neuen Mitglieds.
 * Wird vom Client übermittelt, z. B. beim Anlegen eines Mitglieds im Verwaltungsbereich.
 *
 * Setter verwenden das Fluent-API-Muster zur methodischen Verkettung.
 *
 * Beispiel:
 * <pre>
 *     new MemberCreateDto()
 *         .setSurname("Mustermann")
 *         .setForename("Max")
 *         .setMemberId("1234")
 *         .setEntryDate(LocalDateTime.now())
 *         .setBirthdate(LocalDateTime.of(1990, 1, 1, 0, 0));
 * </pre>
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class MemberCreateDto {

    /**
     * Nachname des Mitglieds (Pflichtfeld, darf nicht leer sein).
     */
    @NotBlank(message = "Nachname ist erforderlich")
    private String surname;

    /**
     * Vorname des Mitglieds (Pflichtfeld).
     */
    @NotBlank(message = "Vorname ist erforderlich")
    private String forename;

    /**
     * Mitgliedsnummer, eindeutig (Pflichtfeld).
     */
    @NotBlank(message = "Mitgliedsnummer ist erforderlich")
    private String memberId;

    /**
     * Eintrittsdatum des Mitglieds (Pflichtfeld).
     */
    @NotNull(message = "Eintrittsdatum ist erforderlich")
    private LocalDateTime entryDate;

    /**
     * Austrittsdatum des Mitglieds (optional).
     */
    private LocalDateTime leavingDate;

    /**
     * Gibt an, ob das Mitglied aktuell aktiv ist.
     * Optional – wenn null, kann es im Backend standardmäßig behandelt werden.
     */
    private Boolean active;

    /**
     * Geburtstag des Mitglieds (Pflichtfeld).
     */
    @NotNull(message = "Geburtstag ist erforderlich")
    private LocalDateTime birthdate;

    /**
     * Gibt an, ob das Mitglied als nicht-gelöscht makiert ist.
     * Standardmäßig auf `true` gesetzt.
     */
    private Boolean aikz = true;

    /**
     * Liste von Gruppen-IDs, denen das Mitglied zugeordnet ist (optional).
     */
    private Collection<Long> groupIds;

    /**
     * Liste von Sparten-IDs, denen das Mitglied zugeordnet ist (optional).
     */
    private Collection<Long> categorieIds;

    // ===== Getter & Setter mit Fluent API =====

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

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public MemberCreateDto setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public LocalDateTime getLeavingDate() {
        return leavingDate;
    }

    public MemberCreateDto setLeavingDate(LocalDateTime leavingDate) {
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

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public MemberCreateDto setBirthdate(LocalDateTime birthdate) {
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
}
