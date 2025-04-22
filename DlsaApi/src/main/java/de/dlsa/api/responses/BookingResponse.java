package de.dlsa.api.responses;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private double countDls;
    private String comment;
    private LocalDateTime doneDate;
    private Boolean canceled;
    private LocalDateTime bookingDate;
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
