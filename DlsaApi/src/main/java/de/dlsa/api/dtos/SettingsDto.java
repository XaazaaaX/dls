package de.dlsa.api.dtos;

import de.dlsa.api.entities.Settings;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.util.Collection;

/**
 * Datenübertragungsobjekt zur Erstellung oder Bearbeitung eines Bereichs (Sectors),
 * z. B. in der Vereinsverwaltung als übergeordnete Einheit für Gruppen.
 *
 * Setter folgen dem Fluent API Stil für methodisches Chaining.
 *
 * Beispiel:
 * <pre>
 *     new SectorDto()
 *         .setSectorname("Verwaltung")
 *         .setGroupIds(List.of(1L, 2L));
 * </pre>
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class SettingsDto {

    /**
     * Jährlicher Stichtag im Format "TT.MM" (z. B. "31.12").
     */
    @NotNull(message = "Stichtag ist erforderlich")
    @Pattern(regexp = "^\\d{2}\\.\\d{2}$", message = "Stichtag muss im Format TT.MM vorliegen (z. B. 31.12)")
    private String dueDate;

    /**
     * Erwartete Anzahl an DLS pro Mitglied.
     */
    @NotNull(message = "Anzahl der Dienstleistungsstunden pro Jahr ist erforderlich")
    @Min(value = 0, message = "Mindestens 0 DLS erwartet")
    private Double countDls;

    /**
     * Geldwert einer DLS-Leistung in Euro.
     */
    @NotNull(message = "Kosten pro Dienstleistungsstunde ist erforderlich")
    @Min(value = 0, message = "Mindestens 0 Euro erwartet")
    private Double costDls;

    /**
     * Altersuntergrenze für DLS-Pflicht.
     */
    @NotNull(message = "Mindestalter für Dienstleistungsstunden ist erforderlich")
    @Min(value = 0, message = "Das Mindestalter beträgt 0 Jahre")
    @Max(value = 99, message = "Das Höchstalter beträgt 99 Jahre")
    private Integer ageFrom;

    /**
     * Altersobergrenze für DLS-Pflicht.
     */
    @NotNull(message = "Maximalalter für Dienstleistungsstunden ist erforderlich")
    @Min(value = 0, message = "Das Mindestalter beträgt 0 Jahre")
    @Max(value = 99, message = "Das Höchstalter beträgt 99 Jahre")
    private Integer ageTo;

    /**
     * Buchungsmethode (z. B. "Anteilig bis zum Stichtag").
     */
    @NotNull(message = "Berechnung beim Jahreslauf ist erforderlich")
    private String bookingMethod;

    /**
     * Gibt an, ob Ausgleichsbuchungen (Clearing) aktiviert sind.
     */
    @NotNull(message = "Ausgleichsbuchungen beim Jahreslauf ist erforderlich")
    private Boolean clearing = true;

    /**
     * Buchungsgranularität (z. B. "Keine", "Wöchentlich", "Monatlich").
     */
    @NotNull(message = "Granularität ist erforderlich")
    private String granularity;

    // ===== Getter & Setter (Fluent API) =====

    public String getDueDate() {
        return dueDate;
    }

    public SettingsDto setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public Double getCountDls() {
        return countDls;
    }

    public SettingsDto setCountDls(Double countDls) {
        this.countDls = countDls;
        return this;
    }

    public Double getCostDls() {
        return costDls;
    }

    public SettingsDto setCostDls(Double costDls) {
        this.costDls = costDls;
        return this;
    }

    public Integer getAgeFrom() {
        return ageFrom;
    }

    public SettingsDto setAgeFrom(Integer ageFrom) {
        this.ageFrom = ageFrom;
        return this;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public SettingsDto setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
        return this;
    }

    public String getBookingMethod() {
        return bookingMethod;
    }

    public SettingsDto setBookingMethod(String bookingMethod) {
        this.bookingMethod = bookingMethod;
        return this;
    }

    public Boolean getClearing() {
        return clearing;
    }

    public SettingsDto setClearing(Boolean clearing) {
        this.clearing = clearing;
        return this;
    }

    public String getGranularity() {
        return granularity;
    }

    public SettingsDto setGranularity(String granularity) {
        this.granularity = granularity;
        return this;
    }
}
