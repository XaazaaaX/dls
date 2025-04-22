package de.dlsa.api.responses;

import de.dlsa.api.entities.GroupChanges;
import de.dlsa.api.entities.MemberChanges;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.time.LocalDateTime;

public class GroupChangesResponse {

    private Long id;
    private LocalDateTime refDate;
    private Boolean oldValue;
    private Boolean newValue;
    private String groupName;

    public Long getId() {
        return id;
    }

    public GroupChangesResponse setId(Long id){
        this.id = id;
        return this;
    }

    public LocalDateTime getRefDate() {
        return refDate;
    }

    public GroupChangesResponse setRefDate(LocalDateTime refDate) {
        this.refDate = refDate;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupChangesResponse setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Boolean getOldValue() {
        return oldValue;
    }

    public GroupChangesResponse setOldValue(Boolean oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public Boolean getNewValue() {
        return newValue;
    }

    public GroupChangesResponse setNewValue(Boolean newValue) {
        this.newValue = newValue;
        return this;
    }

}
