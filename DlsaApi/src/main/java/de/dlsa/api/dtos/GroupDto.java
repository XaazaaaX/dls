package de.dlsa.api.dtos;

import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.responses.MemberResponse;

import java.util.ArrayList;
import java.util.Collection;

public class GroupDto {
    private String groupName;
    private Boolean liberated;


    public String getGroupName() {
        return groupName;
    }

    public GroupDto setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Boolean getLiberated() {
        return liberated;
    }

    public GroupDto setLiberated(Boolean liberated) {
        this.liberated = liberated;
        return this;
    }
}