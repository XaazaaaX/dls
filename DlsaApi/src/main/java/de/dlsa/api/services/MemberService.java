package de.dlsa.api.services;

import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.entities.Member;
import de.dlsa.api.repositories.CategoryRepository;
import de.dlsa.api.repositories.GroupRepository;
import de.dlsa.api.repositories.MemberRepository;
import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.responses.MemberResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public MemberService(
            GroupRepository groupRepository,
            CategoryRepository categoryRepository,
            MemberRepository memberRepository,
            ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    public List<MemberResponse> getMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .sorted(Comparator.comparingLong(Member::getId))
                .map(member -> modelMapper.map(member, MemberResponse.class))
                .collect(Collectors.toList());
    }

    /*
    public List<GroupResponse> createGroups(List<GroupDto> groups) {

        List<Group> newGroups = new ArrayList<>();

        for (GroupDto group: groups) {

            Group mappedGroup = modelMapper.map(group, Group.class);
            newGroups.add(mappedGroup);
        }

        List<Group> addedGroups = groupRepository.saveAll(newGroups);

        return addedGroups.stream()
                .sorted(Comparator.comparingLong(Group::getId))
                .map(group -> modelMapper.map(group, GroupResponse.class))
                .collect(Collectors.toList());
    }

    public GroupResponse updateGroup(long id, GroupDto group) {

        Group existing = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gruppe wurde nicht gefunden!"));


        if (group.getGroupName() != null) {
            existing.setGroupName(group.getGroupName());
        }

        if (group.getLiberated() != null) {
            existing.setLiberated(group.getLiberated());
        }

        Group updatedGroup =  groupRepository.save(existing);

        return modelMapper.map(updatedGroup, GroupResponse.class);

    }
     */
}
