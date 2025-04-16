package de.dlsa.api.services;

import de.dlsa.api.dtos.MemberDto;
import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.CategoryRepository;
import de.dlsa.api.repositories.GroupRepository;
import de.dlsa.api.repositories.MemberChangesRepository;
import de.dlsa.api.repositories.MemberRepository;
import de.dlsa.api.responses.MemberResponse;
import de.dlsa.api.shared.MemberColumn;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final MemberChangesRepository memberChangesRepository;
    private final ModelMapper modelMapper;

    public MemberService(
            GroupRepository groupRepository,
            CategoryRepository categoryRepository,
            MemberRepository memberRepository,
            MemberChangesRepository memberChangesRepository,
            ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
        this.memberRepository = memberRepository;
        this.memberChangesRepository = memberChangesRepository;
        this.modelMapper = modelMapper;
    }

    public List<MemberResponse> getMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .sorted(Comparator.comparingLong(Member::getId))
                .map(member -> modelMapper.map(member, MemberResponse.class))
                .collect(Collectors.toList());
    }


    public MemberResponse createMember(MemberDto member) {

        Member mappedMember = modelMapper.map(member, Member.class);

        if (member.getGroupIds() != null) {
            List<Group> groupList = groupRepository.findAllById(member.getGroupIds());
            mappedMember.setGroups(groupList);
        }

        if (member.getCategorieIds() != null) {
            List<Category> categoryList = categoryRepository.findAllById(member.getCategorieIds());
            mappedMember.setCategories(categoryList);
        }

        Member addedMember = memberRepository.save(mappedMember);

        return modelMapper.map(addedMember, MemberResponse.class);
    }


    public MemberResponse updateMember(long id, MemberDto member) {

        Member existing = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mitglied wurde nicht gefunden!"));


        if (member.getMemberId() != null) {
            existing.setMemberId(member.getMemberId());
        }

        if (member.getForename() != null) {
            existing.setForename(member.getForename() );
        }

        if (member.getSurname() != null) {
            existing.setSurname(member.getSurname());
        }

        if (member.getBirthdate() != null) {
            existing.setBirthdate(member.getBirthdate());
        }

        if (member.getEntryDate() != null && !member.getEntryDate().equals(existing.getEntryDate())) {

            MemberChanges newMemberChanges = new MemberChanges()
                    .setMemberId(existing.getId())
                    .setRefDate(Instant.now())
                    .setColumn(MemberColumn.ENTRYDATE.name())
                    .setNewValue(member.getEntryDate().toString())
                    .setOldValue(existing.getEntryDate().toString());

                memberChangesRepository.save(newMemberChanges);

                existing.setEntryDate(member.getEntryDate());
        }

        if (member.getLeavingDate() != null && !member.getLeavingDate().equals(existing.getLeavingDate())) {

            MemberChanges newMemberChanges = new MemberChanges()
                    .setMemberId(existing.getId())
                    .setRefDate(Instant.now())
                    .setColumn(MemberColumn.LEAVINGDATE.name())
                    .setNewValue(member.getLeavingDate().toString())
                    .setOldValue(existing.getLeavingDate().toString());

            memberChangesRepository.save(newMemberChanges);

            existing.setLeavingDate(member.getLeavingDate());
        }

        if (member.getActive() != null && member.getActive() != existing.getActive()) {

            MemberChanges newMemberChanges = new MemberChanges()
                    .setMemberId(existing.getId())
                    .setRefDate(Instant.now())
                    .setColumn(MemberColumn.ACTIVE.name())
                    .setNewValue(member.getActive().toString())
                    .setOldValue(existing.getActive().toString());

            memberChangesRepository.save(newMemberChanges);

            existing.setActive(member.getActive());
        }

        if (member.getGroupIds() != null) {

            Collection<Group> memberGroups = groupRepository.findAllById(member.getGroupIds());

            Set<Long> existingGroupIds = existing.getGroups().stream()
                    .map(Group::getId)
                    .collect(Collectors.toSet());

            Set<Long> memberGroupIds = memberGroups.stream()
                    .map(Group::getId)
                    .collect(Collectors.toSet());

            if (!existingGroupIds.equals(memberGroupIds)){

                MemberChanges newMemberChanges = new MemberChanges()
                        .setMemberId(existing.getId())
                        .setRefDate(Instant.now())
                        .setColumn(MemberColumn.GROUP.name())
                        .setNewValue(memberGroupIds.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(" ")))
                        .setOldValue(existingGroupIds.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(" ")));

                memberChangesRepository.save(newMemberChanges);

                existing.setGroups(memberGroups);
            }

            /*
            if (!existing.getGroups().equals(memberGroups)){
                existing.setGroups(memberGroups);
            }

             */
        }

        if (member.getCategorieIds() != null) {
            existing.setCategories(categoryRepository.findAllById(member.getCategorieIds()));
        }

        Member updatedMember =  memberRepository.save(existing);

        return modelMapper.map(updatedMember, MemberResponse.class);

    }

}
