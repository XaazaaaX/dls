package de.dlsa.api.services;

import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.*;
import de.dlsa.api.responses.GroupChangesResponse;
import de.dlsa.api.responses.MemberChangesResponse;
import de.dlsa.api.shared.MemberColumn;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorieService {
    private final GroupChangesRepository groupChangesRepository;
    private final GroupRepository groupRepository;
    private final MemberChangesRepository memberChangesRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public HistorieService(
            GroupChangesRepository groupChangesRepository,
            GroupRepository groupRepository,
            MemberChangesRepository memberChangesRepository,
            MemberRepository memberRepository,
            ModelMapper modelMapper) {
        this.groupChangesRepository = groupChangesRepository;
        this.groupRepository = groupRepository;
        this.memberChangesRepository = memberChangesRepository;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    public List<GroupChangesResponse> getGroupChanges() {

        return groupChangesRepository.findAll().stream()
                .sorted(Comparator.comparing(GroupChanges::getRefDate).reversed())
                .map(this::mapGroupChangesToResponse)
                .collect(Collectors.toList());
    }

    private GroupChangesResponse mapGroupChangesToResponse(GroupChanges changes) {

        Group group = groupRepository.findById(changes.getGroupId())
                .orElseThrow(() -> new RuntimeException("Gruppe nicht gefunden"));

        return new GroupChangesResponse()
                .setGroupName(group.getGroupName())
                .setNewValue(changes.getNewValue())
                .setOldValue(changes.getOldValue())
                .setRefDate(changes.getRefDate())
                .setId(changes.getId());
    }

    public List<MemberChangesResponse> getMemberChanges() {

        return memberChangesRepository.findAll().stream()
                .sorted(Comparator.comparing(MemberChanges::getRefDate).reversed())
                .map(this::mapMemberChangesToResponse)
                .collect(Collectors.toList());
    }

    private MemberChangesResponse mapMemberChangesToResponse(MemberChanges changes) {


        Member member = memberRepository.findById(changes.getMemberId())
                .orElseThrow(() -> new RuntimeException("Mitglied nicht gefunden"));


        if (changes.getColumn().equals(MemberColumn.GROUP.name())){

            changes.setOldValue(getGroupNames(changes.getOldValue()));
            changes.setNewValue(getGroupNames(changes.getNewValue()));
        }

        return new MemberChangesResponse()
                .setMemberName(member.getFullName() + " (" + member.getMemberId() + ")")
                .setColumn(changes.getColumn())
                .setTimestamp(changes.getTimestamp())
                .setNewValue(changes.getNewValue())
                .setOldValue(changes.getOldValue())
                .setRefDate(changes.getRefDate())
                .setId(changes.getId());
    }

    private String getGroupNames(String groupIds) {

        if (groupIds == null || groupIds.trim().isEmpty()) {
            return "";
        }

        String[] ids = groupIds.split(" ");
        List<String> groupNames = new ArrayList<>();

        for (String id : ids) {
            Group group = groupRepository.findById(Long.parseLong(id)).orElseThrow(() -> new RuntimeException("Gruppe nicht gefunden"));
            groupNames.add(group.getGroupName());
        }

        return String.join("; ", groupNames);
    }
}
