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

/**
 * Service zur Bereitstellung von Änderungsverläufen (Historie) für Gruppen und Mitglieder.
 *
 * Die Klasse stellt Methoden zur Verfügung, um Änderungen an Gruppen und Mitgliedern
 * aus den Repositories zu laden, aufzubereiten und als lesbare Historien-Datensätze
 * zurückzugeben. Änderungen an Gruppenbefreiungen sowie an Mitgliedseigenschaften
 * (z. B. Gruppe, Eintrittsdatum etc.) werden unterstützt.
 */
@Service
public class HistorieService {

    private final GroupChangesRepository groupChangesRepository;
    private final GroupRepository groupRepository;
    private final MemberChangesRepository memberChangesRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    /**
     * Konstruktor zur Initialisierung des {@link HistorieService}.
     *
     * @param groupChangesRepository Repository für Gruppenänderungen
     * @param groupRepository        Repository für Gruppen
     * @param memberChangesRepository Repository für Mitgliederänderungen
     * @param memberRepository       Repository für Mitglieder
     * @param modelMapper            Mapper zum Konvertieren von Entitäten zu DTOs
     */
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

    /**
     * Gibt eine Liste aller Gruppenänderungen zurück, sortiert nach dem Änderungsdatum (absteigend).
     *
     * @return Liste von {@link GroupChangesResponse}
     */
    public List<GroupChangesResponse> getGroupChanges() {
        return groupChangesRepository.findAll().stream()
                .sorted(Comparator.comparing(GroupChanges::getRefDate).reversed())
                .map(this::mapGroupChangesToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Konvertiert ein {@link GroupChanges}-Objekt in ein {@link GroupChangesResponse}-DTO.
     *
     * @param changes Das Änderungsobjekt der Gruppe
     * @return Konvertiertes Response-Objekt
     * @throws RuntimeException wenn die Gruppe nicht gefunden wird
     */
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

    /**
     * Gibt eine Liste aller Änderungen an Mitgliedern zurück, sortiert nach Änderungsdatum (absteigend).
     *
     * @return Liste von {@link MemberChangesResponse}
     */
    public List<MemberChangesResponse> getMemberChanges() {
        return memberChangesRepository.findAll().stream()
                .sorted(Comparator.comparing(MemberChanges::getRefDate).reversed())
                .map(this::mapMemberChangesToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Konvertiert ein {@link MemberChanges}-Objekt in ein {@link MemberChangesResponse}-DTO.
     * Gruppen-IDs werden in Gruppennamen umgewandelt.
     *
     * @param changes Änderungsobjekt des Mitglieds
     * @return Konvertiertes Response-Objekt
     * @throws RuntimeException wenn das Mitglied nicht gefunden wird
     */
    private MemberChangesResponse mapMemberChangesToResponse(MemberChanges changes) {
        Member member = memberRepository.findById(changes.getMemberId())
                .orElseThrow(() -> new RuntimeException("Mitglied nicht gefunden"));

        if (changes.getColumn().equals(MemberColumn.GROUP.name())) {
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

    /**
     * Wandelt eine durch Leerzeichen getrennte Liste von Gruppen-IDs in Gruppennamen um.
     *
     * @param groupIds String mit Gruppen-IDs (z. B. "1 2 3")
     * @return Lesbarer String mit Gruppennamen, durch Semikolon getrennt
     * @throws RuntimeException wenn eine der Gruppen nicht gefunden wird
     */
    private String getGroupNames(String groupIds) {
        if (groupIds == null || groupIds.trim().isEmpty()) {
            return "";
        }

        String[] ids = groupIds.split(" ");
        List<String> groupNames = new ArrayList<>();

        for (String id : ids) {
            Group group = groupRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new RuntimeException("Gruppe nicht gefunden"));
            groupNames.add(group.getGroupName());
        }

        return String.join("; ", groupNames);
    }
}
