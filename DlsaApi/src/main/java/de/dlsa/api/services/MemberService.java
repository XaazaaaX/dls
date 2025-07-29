package de.dlsa.api.services;

import de.dlsa.api.dtos.*;
import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.*;
import de.dlsa.api.responses.*;
import de.dlsa.api.shared.AlphanumComparator;
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

/**
 * Service zur Verwaltung von Mitgliedsdaten, inklusive Erstellung, Bearbeitung
 * und Massenimport über CSV-Dateien. Außerdem werden Änderungen an relevanten
 * Attributen als Historie gespeichert.
 *
 */
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

    /**
     * Konstruktor zur Initialisierung des MemberService.
     */
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

    /**
     * Gibt eine sortierte Liste aller Mitglieder zurück.
     *
     * @return Liste von {@link MemberResponse}
     */
    public List<MemberResponse> getMembers() {
        List<Member> members = memberRepository.findByAikzTrue();
        return members.stream()
                .sorted(Comparator.comparing(Member::getMemberId, AlphanumComparator.NATURAL_ORDER_CASE_INSENSITIVE))
                .map(member -> modelMapper.map(member, MemberResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Erstellt ein neues Mitglied anhand eines {@link MemberCreateDto}-Objekts.
     *
     * @param member DTO mit den Daten des neuen Mitglieds
     * @return Das erstellte Mitglied als {@link MemberResponse}
     */
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

    /**
     * Importiert mehrere Mitglieder aus einer CSV-Datei und erstellt sie im System.
     * Nicht vorhandene Gruppen oder Sparten werden automatisch erstellt.
     *
     * @param file CSV-Datei im Multipart-Format
     * @return Liste der erfolgreich erstellten Mitglieder
     */
    public List<MemberResponse> uploadMember(MultipartFile file) {
        List<MemberResponse> responseList = new ArrayList<>();

        if (file.isEmpty()) {
            throw new RuntimeException("Keine Datei hochgeladen.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            String headerLine = reader.readLine(); // Header-Zeile
            if (headerLine == null) {
                throw new RuntimeException("Datei enthält keinen Header.");
            }

            String[] headers = headerLine.split(";");
            Map<String, Integer> columnIndexMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                columnIndexMap.put(headers[i].trim().toLowerCase(), i);
            }

            if (!columnIndexMap.containsKey("vorname") || !columnIndexMap.containsKey("nachname") ||
                    !columnIndexMap.containsKey("geburtstag") || !columnIndexMap.containsKey("mitgliedsnummer") ||
                    !columnIndexMap.containsKey("eintritt")) {
                throw new RuntimeException("Pflichtfelder fehlen: vorname, nachname, geburtstag, mitgliedsnummer, eintritt");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] fields = line.split(";", -1);

                String firstName = fields[columnIndexMap.get("vorname")].trim();
                String lastName = fields[columnIndexMap.get("nachname")].trim();
                LocalDateTime birthdate = LocalDate.parse(fields[columnIndexMap.get("geburtstag")].trim(), formatter).atStartOfDay();
                String memberId = fields[columnIndexMap.get("mitgliedsnummer")].trim();
                LocalDateTime entrydate = LocalDate.parse(fields[columnIndexMap.get("eintritt")].trim(), formatter).atStartOfDay();

                List<Long> groupIds = new ArrayList<>();
                if (columnIndexMap.containsKey("gruppen")) {
                    String[] groupNames = fields[columnIndexMap.get("gruppen")].split(",");
                    for (String groupName : groupNames) {
                        String trimmed = groupName.trim();
                        if (!trimmed.isEmpty()) {
                            Group group = groupRepository.findByGroupName(trimmed)
                                    .orElseGet(() -> {
                                        GroupResponse newGroup = groupService.createGroup(new GroupDto()
                                                .setGroupName(trimmed)
                                                .setLiberated(false));
                                        return groupRepository.findById(newGroup.getId()).orElseThrow();
                                    });
                            groupIds.add(group.getId());
                        }
                    }
                }

                List<Long> categoryIds = new ArrayList<>();
                if (columnIndexMap.containsKey("sparten")) {
                    String[] categoryNames = fields[columnIndexMap.get("sparten")].split(",");
                    for (String catName : categoryNames) {
                        String trimmed = catName.trim();
                        if (!trimmed.isEmpty()) {
                            Category category = categoryRepository.findByCategoryName(trimmed)
                                    .orElseGet(() -> {
                                        CategoryResponse newCategory = categoryService.createCategory(new CategoryDto()
                                                .setCategoryName(trimmed));
                                        return categoryRepository.findById(newCategory.getId()).orElseThrow();
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
                } catch (Exception e) {
                    System.out.println("Member: " + newMember.getMemberId() + " konnte nicht hinzugefügt werden!");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Lesen der Datei: " + e.getMessage(), e);
        }

        return responseList;
    }

    /**
     * Aktualisiert ein Mitglied anhand der übergebenen Daten.
     * Änderungen an bestimmten Feldern werden in der Änderungsverfolgung gespeichert.
     *
     * @param id     Die ID des zu aktualisierenden Mitglieds
     * @param member Die neuen Werte
     * @return Das aktualisierte Mitglied als {@link MemberResponse}
     */
    public MemberResponse updateMember(long id, MemberEditDto member) {
        Member existing = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mitglied wurde nicht gefunden!"));

        if (member.getMemberId() != null) existing.setMemberId(member.getMemberId());
        if (member.getForename() != null) existing.setForename(member.getForename());
        if (member.getSurname() != null) existing.setSurname(member.getSurname());
        if (member.getBirthdate() != null) existing.setBirthdate(member.getBirthdate());

        var test1 = member.getEntryDate();
        var test2 = existing.getEntryDate();

        if (member.getEntryDate() != null && !Objects.equals(member.getEntryDate(), existing.getEntryDate())) {
            memberChangesRepository.save(new MemberChanges()
                    .setMemberId(existing.getId())
                    .setRefDate(member.getRefDate())
                    .setColumn(MemberColumn.ENTRYDATE.name())
                    .setNewValue(member.getEntryDate().toString())
                    .setOldValue(existing.getEntryDate().toString()));
            existing.setEntryDate(member.getEntryDate());
        }

        if (member.getLeavingDate() != null && !Objects.equals(member.getLeavingDate(), existing.getLeavingDate())) {
            memberChangesRepository.save(new MemberChanges()
                    .setMemberId(existing.getId())
                    .setRefDate(member.getRefDate())
                    .setColumn(MemberColumn.LEAVINGDATE.name())
                    .setNewValue(Optional.ofNullable(member.getLeavingDate()).map(LocalDateTime::toString).orElse(""))
                    .setOldValue(Optional.ofNullable(existing.getLeavingDate()).map(LocalDateTime::toString).orElse("")));
            existing.setLeavingDate(member.getLeavingDate());
        }

        if (member.getActive() != null && !member.getActive().equals(existing.getActive())) {
            memberChangesRepository.save(new MemberChanges()
                    .setMemberId(existing.getId())
                    .setRefDate(member.getRefDate())
                    .setColumn(MemberColumn.ACTIVE.name())
                    .setNewValue(member.getActive().toString())
                    .setOldValue(existing.getActive().toString()));
            existing.setActive(member.getActive());
        }

        if (member.getGroupIds() != null) {
            Collection<Group> memberGroups = groupRepository.findAllById(member.getGroupIds());
            Set<Long> existingGroupIds = existing.getGroups().stream().map(Group::getId).collect(Collectors.toSet());
            Set<Long> newGroupIds = memberGroups.stream().map(Group::getId).collect(Collectors.toSet());

            if (!existingGroupIds.equals(newGroupIds)) {
                memberChangesRepository.save(new MemberChanges()
                        .setMemberId(existing.getId())
                        .setRefDate(member.getRefDate())
                        .setColumn(MemberColumn.GROUP.name())
                        .setNewValue(newGroupIds.stream().map(String::valueOf).collect(Collectors.joining(" ")))
                        .setOldValue(existingGroupIds.stream().map(String::valueOf).collect(Collectors.joining(" "))));
                existing.setGroups(memberGroups);
            }
        }

        if (member.getCategorieIds() != null) {
            existing.setCategories(categoryRepository.findAllById(member.getCategorieIds()));
        }

        Member updatedMember = memberRepository.save(existing);
        return modelMapper.map(updatedMember, MemberResponse.class);
    }


    /**
     * Löscht ein Mitglied anhand der übergebenen Id.
     * Änderungen an bestimmten Feldern werden in der Änderungsverfolgung gespeichert.
     *
     * @param id     Die ID des zu löschenden Mitglieds
     * @return None
     */
    public void deleteMember(long id) {
        Member existing = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mitglied wurde nicht gefunden!"));

        existing.setAikz(false);

        memberRepository.save(existing);
    }
}
