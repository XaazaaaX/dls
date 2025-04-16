package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class MemberChanges {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;

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

    public Long getId() {
        return id;
    }

    public MemberChanges setId(Long id){
        this.id = id;
        return this;
    }

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
