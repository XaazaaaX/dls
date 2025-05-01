package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Buchungen")
public class Booking extends BaseEntity{
    @Column(name = "anzahldls")
    private Double countDls;
    @Column(name = "bemerkung")
    private String comment;
    @Column(name = "ableistungsdatum")
    private LocalDateTime doneDate;
    @Column(name = "storniert")
    private Boolean canceled = false;
    @Column(name = "buchungsdatum")
    private LocalDateTime bookingDate = LocalDateTime.now();

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

    public Booking setCountDls(Double countDls) {
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

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public Booking setDoneDate(LocalDateTime doneDate) {
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
