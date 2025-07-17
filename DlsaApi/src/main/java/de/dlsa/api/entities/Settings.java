package de.dlsa.api.entities;

import jakarta.persistence.*;

/**
 * Entität zur zentralen Verwaltung von Systemeinstellungen.
 * Enthält Konfigurationswerte wie Stichtag, DLS-Kontingente, Altersgrenzen, Buchungsmodus usw.
 *
 * Diese Einstellungen gelten systemweit und werden bei Berechnungen und Prüfungen verwendet.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Entity
@Table(name = "Einstellungen")
public class Settings extends BaseEntity {

    /**
     * Jährlicher Stichtag im Format "TT.MM" (z. B. "31.12").
     */
    @Column(name = "stichtag")
    private String dueDate;

    /**
     * Erwartete Anzahl an DLS pro Mitglied.
     */
    @Column(name = "anzahldls")
    private Double countDls;

    /**
     * Geldwert einer DLS-Leistung in Euro.
     */
    @Column(name = "kostendls")
    private Double costDls;

    /**
     * Altersuntergrenze für DLS-Pflicht.
     */
    @Column(name = "altervon")
    private Integer ageFrom;

    /**
     * Altersobergrenze für DLS-Pflicht.
     */
    @Column(name = "alterbis")
    private Integer ageTo;

    /**
     * Buchungsmethode (z. B. "Anteilig bis zum Stichtag").
     */
    @Column(name = "buchungsmethode")
    private String bookingMethod;

    /**
     * Gibt an, ob Ausgleichsbuchungen (Clearing) aktiviert sind.
     */
    @Column(name = "ausgleichbuchungen")
    private Boolean clearing = true;

    /**
     * Buchungsgranularität (z. B. "Keine", "Wöchentlich", "Monatlich").
     */
    @Column(name = "granularität")
    private String granularity;

    // ===== Getter & Setter (Fluent API) =====

    public String getDueDate() {
        return dueDate;
    }

    public Settings setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public Double getCountDls() {
        return countDls;
    }

    public Settings setCountDls(Double countDls) {
        this.countDls = countDls;
        return this;
    }

    public Double getCostDls() {
        return costDls;
    }

    public Settings setCostDls(Double costDls) {
        this.costDls = costDls;
        return this;
    }

    public Integer getAgeFrom() {
        return ageFrom;
    }

    public Settings setAgeFrom(Integer ageFrom) {
        this.ageFrom = ageFrom;
        return this;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public Settings setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
        return this;
    }

    public String getBookingMethod() {
        return bookingMethod;
    }

    public Settings setBookingMethod(String bookingMethod) {
        this.bookingMethod = bookingMethod;
        return this;
    }

    public Boolean getClearing() {
        return clearing;
    }

    public Settings setClearing(Boolean clearing) {
        this.clearing = clearing;
        return this;
    }

    public String getGranularity() {
        return granularity;
    }

    public Settings setGranularity(String granularity) {
        this.granularity = granularity;
        return this;
    }
}