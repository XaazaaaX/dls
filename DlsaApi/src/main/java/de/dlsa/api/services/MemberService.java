package de.dlsa.api.services;

import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.dtos.MemberDto;
import de.dlsa.api.dtos.SectorDto;
import de.dlsa.api.entities.Category;
import de.dlsa.api.entities.Group;
import de.dlsa.api.entities.Member;
import de.dlsa.api.entities.Sector;
import de.dlsa.api.repositories.CategoryRepository;
import de.dlsa.api.repositories.GroupRepository;
import de.dlsa.api.repositories.MemberRepository;
import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.responses.MemberResponse;
import de.dlsa.api.responses.SectorResponse;
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


    public List<MemberResponse> createMembers(List<MemberDto> members) {

        List<Member> newMembers = new ArrayList<>();

        for (MemberDto member: members) {

            Member mappedMember = modelMapper.map(member, Member.class);

            List<Group> groupList = groupRepository.findAllById(member.getGroupIds());
            mappedMember.setGroups(groupList);

            List<Category> categoryList = categoryRepository.findAllById(member.getCategorieIds());
            mappedMember.setCategories(categoryList);

            newMembers.add(mappedMember);
        }

        List<Member> addedMembers = memberRepository.saveAll(newMembers);

        return addedMembers.stream()
                .sorted(Comparator.comparingLong(Member::getId))
                .map(member -> modelMapper.map(member, MemberResponse.class))
                .collect(Collectors.toList());
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

        if (member.getEntryDate() != null) {
            existing.setEntryDate(member.getEntryDate() );
        }

        if (member.getLeavingDate() != null) {
            existing.setLeavingDate(member.getLeavingDate());
        }

        if (member.getActive() != null) {
            existing.setActive(member.getActive());
        }

        if (member.getGroupIds() != null) {
            existing.setGroups(groupRepository.findAllById(member.getGroupIds()));
        }

        if (member.getCategorieIds() != null) {
            existing.setCategories(categoryRepository.findAllById(member.getCategorieIds()));
        }

        Member updatedMember =  memberRepository.save(existing);

        return modelMapper.map(updatedMember, MemberResponse.class);

    }

}
