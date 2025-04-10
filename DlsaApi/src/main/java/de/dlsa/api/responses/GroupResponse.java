package de.dlsa.api.responses;

import java.util.ArrayList;
import java.util.Collection;

public class GroupResponse {

    private Long id;
    private String groupName;
    private Boolean liberated;

    public Long getId() {
        return id;
    }

    public GroupResponse setId(Long id){
        this.id = id;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupResponse setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Boolean getLiberated() {
        return liberated;
    }

    public GroupResponse setLiberated(Boolean liberated) {
        this.liberated = liberated;
        return this;
    }
}
