package de.dlsa.api.entities;

import de.dlsa.api.shared.MemberColumn;
import jakarta.persistence.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;

@Table(name = "Mitglieder")
@Entity
public class Member extends BaseEntity{

    @Column(name = "nachname")
    private String surname;
    @Column(name = "vorname")
    private String forename;
    @Column(name = "mitgliedsnummer")
    private String memberId;
    @Column(name = "eintrittsdatum")
    private Instant entryDate;
    @Column(name = "austrittsdatum")
    private Instant  leavingDate;
    @Column(name = "aktiv")
    private Boolean active = true;
    @Column(name = "geburtsdatum")
    private Instant  birthdate;

    private Boolean aikz = true;

    @ManyToMany
    private Collection<Group> groups;

    @ManyToMany
    private Collection<Category> categories;


    @PrePersist
    public void prePersist() {

        /*if (this.basicMember == null) {
            this.basicMember = new BasicMember()
                    .setActive(this.active)
                    .setEntryDate(this.entryDate)
                    .setLeavingDate(this.leavingDate)
                    .setMember(this);
        }
        */
    }

    @PreUpdate
    public void preUpdate() {

    }

    public String getFullName() {
        return forename + " " + surname;
    }

    public String getHtmlName() {
        return "<div style='font-size:0.9em'><strong>"
                + forename
                + " "
                + surname
                + "</strong></div> <div style='font-size:0.8em'>Mitgliedernummer: "
                + memberId + "</div>";
    }

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

    public Instant  getEntryDate() {
        return entryDate;
    }

    public Member setEntryDate(Instant  entryDate) {
        Instant  oldDate = this.entryDate;
        this.entryDate = entryDate;
        //changes.firePropertyChange(MemberColumn.ENTRYDATE.toString(), oldDate,entryDate);
        return this;
    }

    public Instant  getLeavingDate() {
        return leavingDate;
    }

    public Member setLeavingDate(Instant  leavingDate) {
        Instant  oldDate = this.leavingDate;
        this.leavingDate = leavingDate;
        //changes.firePropertyChange(MemberColumn.LEAVINGDATE.toString(),oldDate, leavingDate);
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public Member setActive(Boolean active) {
        Boolean oldValue = this.active;
        this.active = active;
        //changes.firePropertyChange(MemberColumn.ACTIVE.toString(), oldValue,active);
        return this;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public Member setGroups(Collection<Group> groups) {
        Collection<Group> oldGroups = this.groups;
        this.groups = groups;
        //changes.firePropertyChange(MemberColumn.GROUP.toString(), oldGroups,groups);
        return this;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public Member setCategories(Collection<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Instant  getBirthdate() {
        return birthdate;
    }

    public Member setBirthdate(Instant  birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public int getAge(Instant dueDate) {
        if (birthdate == null) {
            return -1;
        }

        /*
        Years age = Years.yearsBetween(new Instant (birthdate), dueDate);
        return age.getYears();
         */

        // birthdate ist vom Typ java.util.Instant  → in Instant umwandeln


        return 0;
    }

    public Boolean getAikz() {
        return aikz;
    }

    public Member setAikz(Boolean aikz) {
        this.aikz = aikz;
        return this;
    }


}

