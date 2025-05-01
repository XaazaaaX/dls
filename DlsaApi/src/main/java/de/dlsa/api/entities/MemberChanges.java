package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "memberchanges")
public class MemberChanges extends BaseEntity {

    @Column(name = "timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "bezugsdatum")
    private LocalDateTime refDate;

    @Column(name = "spaltenname")
    private String column;

    @Column(name = "alterwert")
    private String oldValue;

    @Column(name = "neuerwert")
    private String newValue;

    @Column(name = "memberid")
    private Long memberId;

    public LocalDateTime getRefDate() {
        return refDate;
    }

    public MemberChanges setRefDate(LocalDateTime refDate) {
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public MemberChanges setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
