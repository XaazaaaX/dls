package de.dlsa.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public class BookingDto {
    @NotNull(message = "Dls ist erforderlich")
    @Min(value = 0, message = "DlS darf minimal 0 sein")
    private double countDls;
    @NotBlank(message = "Bemerkung ist erforderlich")
    private String comment;
    @NotNull(message = "Ableistungsdatum ist erforderlich")
    private Instant doneDate;
    @NotNull(message = "Mitglied ist erforderlich")
    private Long memberId;
    @NotNull(message = "Aktion ist erforderlich")
    private Long actionId;

    public double getCountDls() {
        return countDls;
    }

    public BookingDto setCountDls(double countDls) {
        this.countDls = countDls;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public BookingDto setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Instant getDoneDate() {
        return doneDate;
    }

    public BookingDto setDoneDate(Instant doneDate) {
        this.doneDate = doneDate;
        return this;
    }

    public Long getMemberId() {
        return memberId;
    }

    public BookingDto setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public Long getActionId() {
        return actionId;
    }

    public BookingDto setActionId(Long actionId) {
        this.actionId = actionId;
        return this;
    }
}
