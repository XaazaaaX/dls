package de.dlsa.api.entities;

import jakarta.persistence.*;

@Table(name = "Einstellungen")
@Entity
public class Settings extends BaseEntity {
    @Column(name = "stichtag")
    private String dueDate;
    @Column(name = "anzahldls")
    private Double countDls;
    @Column(name = "kostendls")
    private Double costDls;
    @Column(name = "altervon")
    private Integer ageFrom;
    @Column(name = "alterbis")
    private Integer ageTo;
    @Column(name = "buchungsmethode")
    private String bookingMethod;
    @Column(name = "ausgleichbuchungen")
    private Boolean clearing = true;
    @Column(name = "granularität")
    private String granularity;

    public String getDueDate() {
        return dueDate;
    }

    public Settings setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    public Double getCountDls() {
        return countDls;
    }

    public Settings setCountDls(Double countDls) {
        this.countDls = countDls;
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