package de.dlsa.api.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "groupchanges")
public class GroupChanges extends BaseEntity{

    @Column(name = "bezugsdatum")
    private LocalDateTime refDate;

    @Column(name = "alterwert")
    private Boolean oldValue;

    @Column(name = "neuerwert")
    private Boolean newValue;

    @Column(name = "groupid")
    private Long groupId;

    public LocalDateTime getRefDate() {
        return refDate;
    }

    public GroupChanges setRefDate(LocalDateTime refDate) {
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
