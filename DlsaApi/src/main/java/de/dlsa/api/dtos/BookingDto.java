package de.dlsa.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Datenübertragungsobjekt zur Buchung von DLS (Dienstleistungsstunden).
 * Enthält Informationen zur Anzahl der Stunden, zum Mitglied, zur Aktion und zum Datum.
 *
 * Setter sind als Fluent API implementiert, d. h. sie geben das aktuelle Objekt zurück
 * und ermöglichen method chaining.
 *
 * Beispiel:
 * <pre>
 *     new BookingDto()
 *         .setCountDls(5)
 *         .setComment("Arbeit am Sommerfest")
 *         .setDoneDate(LocalDateTime.now())
 *         .setMemberId(1L)
 *         .setActionId(2L);
 * </pre>
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class BookingDto {

    /**
     * Anzahl der geleisteten DLS-Stunden (muss ≥ 0 sein).
     */
    @NotNull(message = "Dls ist erforderlich")
    @Min(value = 0, message = "DlS darf minimal 0 sein")
    private double countDls;

    /**
     * Optionaler Kommentar zur Buchung (z. B. Beschreibung der Tätigkeit).
     */
    private String comment;

    /**
     * Datum der Leistungserbringung (Pflichtfeld).
     */
    @NotNull(message = "Ableistungsdatum ist erforderlich")
    private LocalDateTime doneDate;

    /**
     * ID des zugehörigen Mitglieds (Pflichtfeld).
     */
    @NotNull(message = "Mitglied ist erforderlich")
    private Long memberId;

    /**
     * ID der zugehörigen Aktion (Pflichtfeld).
     */
    @NotNull(message = "Aktion ist erforderlich")
    private Long actionId;

    // ===== Getter & Setter (Fluent API) =====

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

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public BookingDto setDoneDate(LocalDateTime doneDate) {
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
