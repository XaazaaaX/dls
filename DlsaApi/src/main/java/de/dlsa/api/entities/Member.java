package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.*;
import java.util.Collection;

/**
 * Entität zur Repräsentation eines Vereinsmitglieds.
 * Beinhaltet Stammdaten (Name, Mitgliedsnummer), Statusinformationen,
 * Gruppenzugehörigkeit, Spartenzuordnung sowie Zusatzdaten wie AIKZ-Kennzeichen.
 *
 * Die Klasse enthält auch eine Hilfsmethode zur Altersberechnung.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "Mitglieder")
public class Member extends BaseEntity {

    /**
     * Nachname des Mitglieds.
     */
    @Column(name = "nachname")
    private String surname;

    /**
     * Vorname des Mitglieds.
     */
    @Column(name = "vorname")
    private String forename;

    /**
     * Eindeutige Mitgliedsnummer.
     */
    @Column(name = "mitgliedsnummer", unique = true)
    private String memberId;

    /**
     * Eintrittsdatum in den Verein.
     */
    @Column(name = "eintrittsdatum")
    private LocalDateTime entryDate;

    /**
     * Austrittsdatum (optional).
     */
    @Column(name = "austrittsdatum")
    private LocalDateTime leavingDate;

    /**
     * Aktivstatus (true = aktiv).
     */
    @Column(name = "aktiv")
    private Boolean active = true;

    /**
     * Geburtsdatum des Mitglieds.
     */
    @Column(name = "geburtsdatum")
    private LocalDateTime birthdate;

    /**
     * AIKZ-Kennzeichen
     * Standardwert: true.
     */
    private Boolean aikz = true;

    /**
     * Basisdaten des Mitglieds (1:1-Beziehung).
     */
    @OneToOne
    @JoinColumn(name = "basicmember_id")
    private BasicMember basicMember;

    /**
     * Zugehörige Gruppen (Many-to-Many).
     */
    @ManyToMany
    @JoinTable(name = "mitglieder_gruppen")
    private Collection<Group> groups;

    /**
     * Zugeordnete Sparten/Kategorien (Many-to-Many).
     */
    @ManyToMany
    @JoinTable(name = "mitglieder_sparten")
    private Collection<Category> categories;

    /**
     * Callback beim Persistieren – z. B. zur Initialisierung.
     */
    @PrePersist
    public void prePersist() {
        // z. B. automatische Timestamps setzen
    }

    /**
     * Callback beim Update – z. B. zur Änderungsverfolgung.
     */
    @PreUpdate
    public void preUpdate() {
        // z. B. Audit-Logik
    }

    // ===== Hilfsmethoden =====

    /**
     * Liefert den vollständigen Namen (Vorname + Nachname).
     */
    public String getFullName() {
        return forename + " " + surname;
    }

    /**
     * Berechnet das Alter zum gegebenen Stichtag.
     *
     * @param dueDate Stichtagsdatum
     * @return Alter in Jahren oder 0 bei unvollständigen Daten
     */
    public int getAge(LocalDateTime dueDate) {
        if (birthdate == null || dueDate == null) {
            return 0;
        }
        return Period.between(birthdate.toLocalDate(), dueDate.toLocalDate()).getYears();
    }

    // ===== Getter & Setter (Fluent API) =====

    public String getSurname() {
        return surname;
    }

    public Member setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getForename() {
        return forename;
    }

    public Member setForename(String forename) {
        this.forename = forename;
        return this;
    }

    public String getMemberId() {
        return memberId;
    }

    public Member setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public Member setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public LocalDateTime getLeavingDate() {
        return leavingDate;
    }

    public Member setLeavingDate(LocalDateTime leavingDate) {
        this.leavingDate = leavingDate;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public Member setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public Member setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public Boolean getAikz() {
        return aikz;
    }

    public Member setAikz(Boolean aikz) {
        this.aikz = aikz;
        return this;
    }

    public BasicMember getBasicMember() {
        return basicMember;
    }

    public Member setBasicMember(BasicMember basicMember) {
        this.basicMember = basicMember;
        return this;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public Member setGroups(Collection<Group> groups) {
        this.groups = groups;
        return this;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public Member setCategories(Collection<Category> categories) {
        this.categories = categories;
        return this;
    }
}
