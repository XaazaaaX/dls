package de.dlsa.api.services;

import de.dlsa.api.dtos.CategoryDto;
import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.dtos.MemberCreateDto;
import de.dlsa.api.dtos.MemberEditDto;
import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.*;
import de.dlsa.api.responses.CategoryResponse;
import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.responses.MemberResponse;
import de.dlsa.api.shared.MemberColumn;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;
    private final BasicMemberRepository basicMemberRepository;
    private final MemberRepository memberRepository;
    private final MemberChangesRepository memberChangesRepository;
    private final GroupService groupService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public MemberService(
            GroupRepository groupRepository,
            CategoryRepository categoryRepository,
            BasicMemberRepository basicMemberRepository,
            MemberRepository memberRepository,
            MemberChangesRepository memberChangesRepository,
            CategoryService categoryService,
            GroupService groupService,
            ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
        this.basicMemberRepository = basicMemberRepository;
        this.memberRepository = memberRepository;
        this.memberChangesRepository = memberChangesRepository;
        this.groupService = groupService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    public List<MemberResponse> getMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .sorted(Comparator.comparingLong(Member::getId))
                .map(member -> modelMapper.map(member, MemberResponse.class))
                .collect(Collectors.toList());
    }

    public MemberResponse createMember(MemberCreateDto member) {

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

        BasicMember bMember = new BasicMember()
                .setMember(addedMember)
                .setActive(addedMember.getActive())
                .setEntryDate(addedMember.getEntryDate())
                .setLeavingDate(addedMember.getLeavingDate());

        BasicMember addedBasicMember = basicMemberRepository.save(bMember);

        addedMember.setBasicMember(addedBasicMember);
        Member finalMember = memberRepository.save(addedMember);

        return modelMapper.map(finalMember, MemberResponse.class);
    }

    public List<MemberResponse> uploadMember(MultipartFile file) {

        List<MemberResponse> responseList = new ArrayList<>();

        if (file.isEmpty()) {
            throw new RuntimeException("Keine Datei hochgeladen.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String headerLine = reader.readLine(); // Erste Zeile (Header)
            if (headerLine == null) {
                throw new RuntimeException("Datei enthält keinen Header.");
            }

            // Dynamische Zuordnung der Spaltenindizes
            String[] headers = headerLine.split(";");
            Map<String, Integer> columnIndexMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                columnIndexMap.put(headers[i].trim().toLowerCase(), i);
            }

            // Prüfung: Pflichtfelder vorhanden?
            if (!columnIndexMap.containsKey("vorname") || !columnIndexMap.containsKey("nachname") || !columnIndexMap.containsKey("geburtstag") || !columnIndexMap.containsKey("mitgliedsnummer") || !columnIndexMap.containsKey("eintritt")) {
                throw new RuntimeException("Pflichtfelder 'vorname' oder 'nachname' fehlen.");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Leere Zeilen überspringen

                String[] fields = line.split(";", -1); // auch leere Felder erfassen

                String firstName = fields[columnIndexMap.get("vorname")].trim();
                String lastName = fields[columnIndexMap.get("nachname")].trim();
                LocalDateTime birthdate = LocalDate.parse(fields[columnIndexMap.get("geburtstag")].trim(), formatter).atStartOfDay();
                String memberId = fields[columnIndexMap.get("mitgliedsnummer")].trim();
                LocalDateTime entrydate = LocalDate.parse(fields[columnIndexMap.get("eintritt")].trim(), formatter).atStartOfDay();

                // Gruppen verarbeiten
                List<Long> groupIds = new ArrayList<>();
                if (columnIndexMap.containsKey("gruppen")) {
                    String groupField = fields[columnIndexMap.get("gruppen")];
                    String[] groupNames = groupField.split(",");
                    for (String groupName : groupNames) {
                        String trimmed = groupName.trim();
                        if (!trimmed.isEmpty()) {
                            Group group = groupRepository.findByGroupName(trimmed)
                                    .orElseGet(() -> {
                                        GroupResponse newGroup = groupService.createGroup(new GroupDto()
                                                .setGroupName(trimmed)
                                                .setLiberated(false));

                                        return groupRepository.findById(newGroup.getId()).orElseThrow(() -> new RuntimeException("Gruppe wurde nicht gefunden!"));
                                    });
                            groupIds.add(group.getId());
                        }
                    }
                }

                // Sparten verarbeiten
                List<Long> categoryIds = new ArrayList<>();
                if (columnIndexMap.containsKey("sparten")) {
                    String categoryField = fields[columnIndexMap.get("sparten")];
                    String[] categoryNames = categoryField.split(",");
                    for (String catName : categoryNames) {
                        String trimmed = catName.trim();
                        if (!trimmed.isEmpty()) {
                            Category category = categoryRepository.findByCategoryName(trimmed)
                                    .orElseGet(() -> {
                                        CategoryResponse newCategory = categoryService.createCategory(new CategoryDto()
                                                .setCategoryName(trimmed));

                                        return categoryRepository.findById(newCategory.getId()).orElseThrow(() -> new RuntimeException("Sparte wurde nicht gefunden!"));
                                    });
                            categoryIds.add(category.getId());
                        }
                    }
                }

                MemberCreateDto newMember = new MemberCreateDto()
                        .setSurname(lastName)
                        .setForename(firstName)
                        .setMemberId(memberId)
                        .setEntryDate(entrydate)
                        .setBirthdate(birthdate)
                        .setGroupIds(groupIds)
                        .setCategorieIds(categoryIds);

                try {
                    MemberResponse finalMember = createMember(newMember);
                    responseList.add(finalMember);
                } catch (Exception e){
                    System.out.println("Member: " + newMember.getMemberId() + " konnte nicht hinzugefügt werden!");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Lesen der Datei: " + e.getMessage(), e);
        }

        return responseList;
    }


    public MemberResponse updateMember(long id, MemberEditDto member) {

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
                    .setRefDate(member.getRefDate())
                    .setColumn(MemberColumn.ENTRYDATE.name())
                    .setNewValue(member.getEntryDate().toString())
                    .setOldValue(existing.getEntryDate().toString());

                memberChangesRepository.save(newMemberChanges);

                existing.setEntryDate(member.getEntryDate());
        }

        if (member.getLeavingDate() != null && !member.getLeavingDate().equals(existing.getLeavingDate())) {

            MemberChanges newMemberChanges = new MemberChanges()
                    .setMemberId(existing.getId())
                    .setRefDate(member.getRefDate())
                    .setColumn(MemberColumn.LEAVINGDATE.name())
                    .setNewValue(member.getLeavingDate() != null ? member.getLeavingDate().toString()  : "")
                    .setOldValue(existing.getLeavingDate() != null ? existing.getLeavingDate().toString() : "");

            memberChangesRepository.save(newMemberChanges);

            existing.setLeavingDate(member.getLeavingDate());
        }

        if (member.getActive() != null && member.getActive() != existing.getActive()) {

            MemberChanges newMemberChanges = new MemberChanges()
                    .setMemberId(existing.getId())
                    .setRefDate(member.getRefDate())
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
                        .setRefDate(member.getRefDate())
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
        }

        if (member.getCategorieIds() != null) {
            existing.setCategories(categoryRepository.findAllById(member.getCategorieIds()));
        }

        Member updatedMember =  memberRepository.save(existing);

        return modelMapper.map(updatedMember, MemberResponse.class);

    }

}
