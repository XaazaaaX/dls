package de.dlsa.api.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberChangesResponse {
    private Long id;
    private LocalDateTime timestamp;
    private LocalDateTime refDate;
    private String column;
    private String oldValue;
    private String newValue;
    private String memberName;

    public Long getId() {
        return id;
    }

    public MemberChangesResponse setId(Long id){
        this.id = id;
        return this;
    }

    public LocalDateTime getRefDate() {
        return refDate;
    }

    public MemberChangesResponse setRefDate(LocalDateTime refDate) {
        this.refDate = refDate;
        return this;
    }

    public String getColumn() {
        return column;
    }

    public MemberChangesResponse setColumn(String column) {
        this.column = column;
        return this;
    }

    public String getOldValue() {
        return oldValue;
    }

    public MemberChangesResponse setOldValue(String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public String getNewValue() {
        return newValue;
    }

    public MemberChangesResponse setNewValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public String getMemberName() {
        return memberName;
    }

    public MemberChangesResponse setMemberName(String memberName) {
        this.memberName = memberName;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public MemberChangesResponse setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
