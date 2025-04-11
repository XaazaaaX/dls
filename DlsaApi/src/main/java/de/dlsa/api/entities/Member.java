package de.dlsa.api.entities;

import de.dlsa.api.shared.MemberColumn;
import jakarta.persistence.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Table(name = "Mitglieder")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(name = "nachname")
    private String surname;
    @Column(name = "vorname")
    private String forename;
    @Column(name = "mitgliedsnummer")
    private String memberId;
    @Column(name = "eintrittsdatum")
    private Date entryDate;
    @Column(name = "austrittsdatum")
    private Date leavingDate;
    @Column(name = "aktiv")
    private Boolean active = true;
    @Column(name = "geburtsdatum")
    private Date birthdate;

    private Boolean aikz = true;

    /*
    @OneToOne(cascade = CascadeType.ALL)
    private BasicMember basicMember;
     */

    @ManyToMany()
    private Collection<Group> groups = new ArrayList<Group>();

    @ManyToMany()
    private Collection<Category> categories = new ArrayList<Category>();

    /*
    @Transient
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

     */

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

    public Long getId() {
        return id;
    }

    public Member setId(Long id){
        this.id = id;
        return this;
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

    public Date getEntryDate() {
        return entryDate;
    }

    public Member setEntryDate(Date entryDate) {
        Date oldDate = this.entryDate;
        this.entryDate = entryDate;
        //changes.firePropertyChange(MemberColumn.ENTRYDATE.toString(), oldDate,entryDate);
        return this;
    }

    public Date getLeavingDate() {
        return leavingDate;
    }

    public Member setLeavingDate(Date leavingDate) {
        Date oldDate = this.leavingDate;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public Member setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public int getAge(LocalDate dueDate) {
        if (birthdate == null) {
            return -1;
        }

        /*
        Years age = Years.yearsBetween(new LocalDate (birthdate), dueDate);
        return age.getYears();
         */

        // birthdate ist vom Typ java.util.Date → in LocalDate umwandeln
        LocalDate birthLocalDate = birthdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return Period.between(birthLocalDate, dueDate).getYears();
    }

    /*
    public BasicMember getBasicMember() {
        return basicMember;
    }

    public Member setBasicMember(BasicMember basicMember) {
        this.basicMember = basicMember;
        return this;
    }

    public Boolean getAikz() {
        return aikz;
    }

    public Member setAikz(Boolean aikz) {
        this.aikz = aikz;
        return this;
    }
     */

}

