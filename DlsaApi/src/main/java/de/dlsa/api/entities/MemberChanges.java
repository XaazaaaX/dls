package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class MemberChanges extends BaseEntity {

    private Instant timestamp = Instant.now();

    @Column(name = "bezugsdatum")
    private Instant refDate;

    @Column(name = "spaltenname")
    private String column;

    @Column(name = "alterWert")
    private String oldValue;

    @Column(name = "neuerWert")
    private String newValue;

    private Long memberId;

    public Instant getRefDate() {
        return refDate;
    }

    public MemberChanges setRefDate(Instant refDate) {
        this.refDate = refDate;
        return this;
    }

    public String getColumn() {
        return column;
    }

    public MemberChanges setColumn(String column) {
        this.column = column;
        return this;
    }

    public String getOldValue() {
        return oldValue;
    }

    public MemberChanges setOldValue(String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public String getNewValue() {
        return newValue;
    }

    public MemberChanges setNewValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public Long getMemberId() {
        return memberId;
    }

    public MemberChanges setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public MemberChanges setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
