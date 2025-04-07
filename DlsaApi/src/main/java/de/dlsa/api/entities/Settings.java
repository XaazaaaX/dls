package de.dlsa.api.entities;

import jakarta.persistence.*;

@Table(name = "Einstellungen")
@Entity
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(name = "stichtag")
    private String dueDate;
    @Column(name = "anzahlDls")
    private Double countDls;
    @Column(name = "kostenDls")
    private Double costDls;
    @Column(name = "alterVon")
    private Integer ageFrom;
    @Column(name = "alterBis")
    private Integer ageTo;
    @Column(name = "buchungsmethode")
    private String bookingMethod;
    @Column(name = "ausgleichbuchungen")
    private Boolean clearing = true;
    @Column(name = "granularität")
    private String granularity;

    public Long getId() {
        return id;
    }

    public Settings setId(Long id) {
        this.id = id;
        return this;
    }

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