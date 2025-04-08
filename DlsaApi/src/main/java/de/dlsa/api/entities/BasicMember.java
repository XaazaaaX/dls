package de.dlsa.api.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class BasicMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "aktiv")
    private Boolean active;

    @Column(name = "eintrittsdatum")
    private Date entryDate;

    @Column(name = "austrittsdatum")
    private Date leavingDate;

    @OneToOne
    private Member member;

    public Boolean getActive() {
        return active;
    }

    public BasicMember setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public BasicMember setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public Date getLeavingDate() {
        return leavingDate;
    }

    public BasicMember setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
        return this;
    }

    public Member getMember() {
        return member;
    }

    public BasicMember setMember(Member member) {
        this.member = member;
        return this;
    }
}
