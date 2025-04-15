package de.dlsa.api.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberChanges {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    private Long id;

    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "bezugsdatum")
    private LocalDateTime refDate;

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
