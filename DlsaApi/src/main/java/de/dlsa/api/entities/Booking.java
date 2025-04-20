package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "Buchungen")
public class Booking extends BaseEntity{
    @Column(name = "anzahlDls")
    private double countDls;
    @Column(name = "bemerkung")
    private String comment;
    @Column(name = "ableistungsDatum")
    private Instant doneDate;
    @Column(name = "storniert")
    private Boolean canceled = false;
    @Column(name = "buchungsdatum")
    private Instant bookingDate = Instant.now();

    @ManyToOne
    private Member member;

    @ManyToOne
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private Action action;

    /*
    public Booking() {

    }

    public Booking(double countDls, String comment, Date doneDate,
                   Member member, Campaign campaign) {
        super();
        this.countDls = countDls;
        this.comment = comment;
        this.doneDate = doneDate;
        this.member = member;
        this.campaign = campaign;
    }
    */

    public double getCountDls() {
        return countDls;
    }

    public Booking setCountDls(double countDls) {
        this.countDls = countDls;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Booking setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Instant getDoneDate() {
        return doneDate;
    }

    public Booking setDoneDate(Instant doneDate) {
        this.doneDate = doneDate;
        return this;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public Booking setCanceled(Boolean canceled) {
        this.canceled = canceled;
        return this;
    }

    public Member getMember() {
        return member;
    }

    public Booking setMember(Member member) {
        this.member = member;
        return this;
    }

    public Action getAction() {
        return action;
    }

    public Booking setAction(Action action) {
        this.action = action;
        return this;
    }
}
