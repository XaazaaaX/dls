package de.dlsa.api.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Entity
public class GroupChanges extends BaseEntity{

    @Column(name = "bezugsdatum")
    private Instant refDate;

    @Column(name = "alterWert")
    private Boolean oldValue;

    @Column(name = "neuerWert")
    private Boolean newValue;

    private Long groupId;

    public Instant getRefDate() {
        return refDate;
    }

    public GroupChanges setRefDate(Instant refDate) {
        this.refDate = refDate;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public GroupChanges setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public Boolean getOldValue() {
        return oldValue;
    }

    public GroupChanges setOldValue(Boolean oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public Boolean getNewValue() {
        return newValue;
    }

    public GroupChanges setNewValue(Boolean newValue) {
        this.newValue = newValue;
        return this;
    }
}
