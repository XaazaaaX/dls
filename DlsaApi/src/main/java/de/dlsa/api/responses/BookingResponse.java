package de.dlsa.api.responses;

import java.time.Instant;

public class BookingResponse {
    private Long id;
    private double countDls;
    private String comment;
    private Instant doneDate;
    private Boolean canceled;
    private Instant bookingDate;
    private MemberResponse member;
    private ActionResponse action;

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

    public Instant getDoneDate() {
        return doneDate;
    }

    public BookingResponse setDoneDate(Instant doneDate) {
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

    public Instant getBookingDate() {
        return bookingDate;
    }

    public BookingResponse setBookingDate(Instant bookingDate) {
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
