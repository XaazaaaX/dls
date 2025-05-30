package de.dlsa.api.services;

import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.entities.BasicGroup;
import de.dlsa.api.entities.Group;
import de.dlsa.api.entities.GroupChanges;
import de.dlsa.api.repositories.BasicGroupRepository;
import de.dlsa.api.repositories.GroupChangesRepository;
import de.dlsa.api.repositories.GroupRepository;
import de.dlsa.api.responses.GroupResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final BasicGroupRepository basicGroupRepository;
    private final GroupChangesRepository groupChangesRepository;
    private final ModelMapper modelMapper;

    public GroupService(
            GroupRepository groupRepository,
            BasicGroupRepository basicGroupRepository,
            GroupChangesRepository groupChangesRepository,
            ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.basicGroupRepository = basicGroupRepository;
        this.groupChangesRepository = groupChangesRepository;
        this.modelMapper = modelMapper;
    }

    public List<GroupResponse> getGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .sorted(Comparator.comparingLong(Group::getId))
                .map(group -> modelMapper.map(group, GroupResponse.class))
                .collect(Collectors.toList());
    }

    public GroupResponse createGroup(GroupDto group) {

        Group mappedGroup = modelMapper.map(group, Group.class);

        Group addedGroup = groupRepository.save(mappedGroup);

        BasicGroup bGroup = new BasicGroup()
                .setLiberate(addedGroup.getLiberated())
                .setGroupName(addedGroup.getGroupName())
                .setGroup(addedGroup);

        BasicGroup addedBasicGroup = basicGroupRepository.save(bGroup);

        addedGroup.setBasicGroup(addedBasicGroup);
        Group finalGroup = groupRepository.save(addedGroup);

        return modelMapper.map(finalGroup, GroupResponse.class);
    }

    public GroupResponse updateGroup(long id, GroupDto group) {

        Group existing = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gruppe wurde nicht gefunden!"));


        if (group.getGroupName() != null) {
            existing.setGroupName(group.getGroupName());
        }

        if (group.getLiberated() != null && group.getLiberated() != existing.getLiberated()) {

            GroupChanges newGroupChanges = new GroupChanges()
                    .setGroupId(existing.getId())
                    .setNewValue(group.getLiberated())
                    .setOldValue(existing.getLiberated())
                    .setRefDate(LocalDateTime.now());

            groupChangesRepository.save(newGroupChanges);

            existing.setLiberated(group.getLiberated());
        }

        Group updatedGroup =  groupRepository.save(existing);

        return modelMapper.map(updatedGroup, GroupResponse.class);

    }
}