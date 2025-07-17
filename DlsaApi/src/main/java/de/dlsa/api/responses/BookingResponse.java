package de.dlsa.api.responses;

import java.time.LocalDateTime;

/**
 * Response-Klasse zur Repräsentation einer DLS-Buchung.
 * Wird verwendet, um Informationen über erbrachte Leistungen
 * in strukturierter Form an das Frontend zurückzugeben.
 *
 * Beinhaltet sowohl Basisdaten (Anzahl, Datum, Kommentar),
 * als auch verknüpfte Entitäten wie {@link MemberResponse} und {@link ActionResponse}.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class BookingResponse {

    /**
     * Eindeutige ID der Buchung.
     */
    private Long id;

    /**
     * Anzahl erbrachter Dienstleistungsstunden.
     */
    private double countDls;

    /**
     * Optionaler Freitext-Kommentar.
     */
    private String comment;

    /**
     * Datum, an dem die Leistung erbracht wurde.
     */
    private LocalDateTime doneDate;

    /**
     * Gibt an, ob die Buchung storniert wurde.
     */
    private Boolean canceled;

    /**
     * Zeitpunkt der Erfassung der Buchung.
     */
    private LocalDateTime bookingDate;

    /**
     * Verknüpftes Mitglied, das die Leistung erbracht hat.
     */
    private MemberResponse member;

    /**
     * Verknüpfte Aktion bzw. Kampagne.
     */
    private ActionResponse action;

    // --- Getter & Setter (mit Fluent-API) ---

    public Long getId() {
        return id;
    }

    public BookingResponse setId(Long id){
        this.id = id;
        return this;
    }

    public double getCountDls() {
        return countDls;
    }

    public BookingResponse setCountDls(double countDls) {
        this.countDls = countDls;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public BookingResponse setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public BookingResponse setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
        return this;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public BookingResponse setCanceled(Boolean canceled) {
        this.canceled = canceled;
        return this;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public BookingResponse setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public MemberResponse getMember() {
        return member;
    }

    public BookingResponse setMember(MemberResponse member) {
        this.member = member;
        return this;
    }

    public ActionResponse getAction() {
        return action;
    }

    public BookingResponse setAction(ActionResponse action) {
        this.action = action;
        return this;
    }
}
