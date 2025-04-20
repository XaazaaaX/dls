package de.dlsa.api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "jahre")
public class Year extends BaseEntity {

    @Column(name = "jahr")
    private int year;

    public int getYear() {
        return year;
    }

    public Year setYear(int year) {
        this.year = year;
        return this;
    }
}
