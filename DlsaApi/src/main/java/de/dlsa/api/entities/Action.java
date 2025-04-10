package de.dlsa.api.entities;

import jakarta.persistence.*;

@Table(name = "Aktionen")
@Entity
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "jahr")
    private String year;

    @Column(name = "beschreibung")
    private String description;

    @ManyToOne
    private Member contact;

    public Long getId() {
        return id;
    }

    public Action setId(Long id){
        this.id = id;
        return this;
    }
    public String getYear() {
        return year;
    }

    public Action setYear(String year) {
        this.year = year;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Action setDescription(String description) {
        this.description = description;
        return this;
    }

    public Member getContact() {
        return contact;
    }

    public Action setContact(Member contact) {
        this.contact = contact;
        return this;
    }

}