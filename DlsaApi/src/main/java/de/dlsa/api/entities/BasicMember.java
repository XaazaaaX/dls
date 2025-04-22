
package de.dlsa.api.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
public class BasicMember extends BaseEntity {

    @Column(name = "aktiv")
    private Boolean active;

    @Column(name = "eintrittsdatum")
    private LocalDateTime entryDate;

    @Column(name = "austrittsdatum")
    private LocalDateTime leavingDate;

    @OneToOne
    private Member member;

    public Boolean getActive() {
        return active;
    }

    public BasicMember setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public BasicMember setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public LocalDateTime getLeavingDate() {
        return leavingDate;
    }

    public BasicMember setLeavingDate(LocalDateTime leavingDate) {
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