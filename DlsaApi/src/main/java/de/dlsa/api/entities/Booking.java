package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Repräsentiert eine Buchungseinheit (z. B. eine erledigte Dienstleistung).
 * Jede Buchung ist eindeutig einem Mitglied und einer Aktion (Kampagne) zugeordnet.
 *
 * Felder umfassen u. a. Anzahl der DLS, Datum der Ableistung, Kommentare, Status und Erfassungszeitpunkt.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "Buchungen")
public class Booking extends BaseEntity {

    /**
     * Anzahl der geleisteten DLS-Stunden (Pflichtfeld).
     */
    @Column(name = "anzahldls", nullable = false)
    private Double countDls;

    /**
     * Freitext-Kommentar zur Buchung (optional).
     */
    @Column(name = "bemerkung")
    private String comment;

    /**
     * Datum, an dem die DLS-Leistung tatsächlich erbracht wurde (z. B. Einsatztermin).
     */
    @Column(name = "ableistungsdatum")
    private LocalDateTime doneDate;

    /**
     * Kennzeichnung, ob diese Buchung storniert wurde (Standard: false).
     */
    @Column(name = "storniert", nullable = false)
    private Boolean canceled = false;

    /**
     * Zeitpunkt der Erfassung (Standard: {@code LocalDateTime.now()} bei Erstellung).
     */
    @Column(name = "buchungsdatum", nullable = false)
    private LocalDateTime bookingDate = LocalDateTime.now();

    /**
     * Referenz auf das zugehörige Mitglied.
     * Pflichtfeld – ohne Mitglied ist eine Buchung nicht gültig.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    /**
     * Referenz auf die zugehörige Aktion (z. B. Projekt oder Arbeitsdienst).
     * Pflichtfeld.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private Action action;

    // ===== Getter & Setter (Fluent API) =====

    public Double getCountDls() {
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

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public Booking setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
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
