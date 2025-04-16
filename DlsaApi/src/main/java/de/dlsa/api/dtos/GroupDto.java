package de.dlsa.api.dtos;

import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.responses.MemberResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class GroupDto {
    @NotBlank(message = "Gruppenbezeichnung ist erforderlich")
    private String groupName;
    @NotNull(message = "DLS-Befreit? ist erforderlich")
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