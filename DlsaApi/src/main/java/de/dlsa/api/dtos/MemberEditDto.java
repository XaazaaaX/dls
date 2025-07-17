package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Datenübertragungsobjekt zur Bearbeitung eines bestehenden Mitglieds.
 * Im Unterschied zu {@link MemberCreateDto} enthält dieses DTO zusätzlich ein Bezugsdatum (refDate),
 * das z. B. für rückwirkende Buchungen oder Verlaufsbetrachtungen verwendet werden kann.
 *
 * Alle relevanten Felder sind entweder erforderlich oder optional, je nach Validierungslogik im Backend.
 * Setter folgen dem Fluent API Stil.
 *
 * Beispiel:
 * <pre>
 *     new MemberEditDto()
 *         .setMemberId("1111")
 *         .setRefDate(LocalDateTime.now())
 *         .setSurname("Musterfrau")
 *         .setForename("Mina");
 * </pre>
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class MemberEditDto {

    /**
     * Bezugsdatum für die Bearbeitung (z. B. Änderungsstichtag).
     * Pflichtfeld.
     */
    @NotNull(message = "Bezugsdatum ist erforderlich")
    private LocalDateTime refDate;

    /**
     * Nachname des Mitglieds (Pflichtfeld).
     */
    @NotBlank(message = "Nachname ist erforderlich")
    private String surname;

    /**
     * Vorname des Mitglieds (Pflichtfeld).
     */
    @NotBlank(message = "Vorname ist erforderlich")
    private String forename;

    /**
     * Mitgliedsnummer (Pflichtfeld).
     */
    @NotBlank(message = "Mitgliedsnummer ist erforderlich")
    private String memberId;

    /**
     * Eintrittsdatum (Pflichtfeld).
     */
    @NotNull(message = "Eintrittsdatum ist erforderlich")
    private LocalDateTime entryDate;

    /**
     * Austrittsdatum (optional).
     */
    private LocalDateTime leavingDate;

    /**
     * Aktiver Mitgliedsstatus (optional).
     */
    private Boolean active;

    /**
     * Geburtsdatum des Mitglieds (Pflichtfeld).
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

    // ===== Getter & Setter (Fluent API) =====

    public LocalDateTime getRefDate() {
        return refDate;
    }

    public MemberEditDto setRefDate(LocalDateTime refDate) {
        this.refDate = refDate;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public MemberEditDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getForename() {
        return forename;
    }

    public MemberEditDto setForename(String forename) {
        this.forename = forename;
        return this;
    }

    public String getMemberId() {
        return memberId;
    }

    public MemberEditDto setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public MemberEditDto setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public LocalDateTime getLeavingDate() {
        return leavingDate;
    }

    public MemberEditDto setLeavingDate(LocalDateTime leavingDate) {
        this.leavingDate = leavingDate;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public MemberEditDto setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public MemberEditDto setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public Boolean getAikz() {
        return aikz;
    }

    public MemberEditDto setAikz(Boolean aikz) {
        this.aikz = aikz;
        return this;
    }

    public Collection<Long> getGroupIds() {
        return groupIds;
    }

    public MemberEditDto setGroupIds(Collection<Long> groupIds) {
        this.groupIds = groupIds;
        return this;
    }

    public Collection<Long> getCategorieIds() {
        return categorieIds;
    }

    public MemberEditDto setCategorieIds(Collection<Long> categorieIds) {
        this.categorieIds = categorieIds;
        return this;
    }
}
