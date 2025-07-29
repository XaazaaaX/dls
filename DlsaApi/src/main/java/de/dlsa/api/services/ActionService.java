package de.dlsa.api.services;

import de.dlsa.api.dtos.ActionDto;
import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.ActionRepository;
import de.dlsa.api.repositories.MemberRepository;
import de.dlsa.api.responses.ActionResponse;
import de.dlsa.api.shared.AlphanumComparator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service-Klasse zur Verwaltung von Aktionen (z. B. Kampagnen).
 * Beinhaltet Methoden zum Erstellen, Bearbeiten und Abrufen von Aktionen.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Service
public class ActionService {

    private final ActionRepository actionRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    /**
     * Konstruktor zur Initialisierung mit benötigten Abhängigkeiten.
     *
     * @param actionRepository Repository für Aktionen
     * @param memberRepository Repository für Mitglieder
     * @param modelMapper      Mapper zur DTO-Konvertierung
     */
    public ActionService(
            ActionRepository actionRepository,
            MemberRepository memberRepository,
            ModelMapper modelMapper) {
        this.actionRepository = actionRepository;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Ruft alle vorhandenen Aktionen sortiert nach ID ab.
     *
     * @return Liste aller Aktionen als {@link ActionResponse}
     */
    public List<ActionResponse> getActions() {
        return actionRepository.findAll().stream()
                .sorted(Comparator.comparing(Action::getDescription, AlphanumComparator.NATURAL_ORDER_CASE_INSENSITIVE))
                .map(action -> modelMapper.map(action, ActionResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Erstellt eine neue Aktion basierend auf dem übergebenen DTO.
     *
     * @param action DTO mit Aktionsdaten
     * @return Erstellte Aktion als {@link ActionResponse}
     * @throws RuntimeException Wenn das zugeordnete Mitglied nicht gefunden wird
     */
    public ActionResponse createAction(ActionDto action) {
        Action mappedAction = modelMapper.map(action, Action.class);

        Member member = memberRepository.findById(action.getContactId())
                .orElseThrow(() -> new RuntimeException("Mitglied nicht gefunden"));
        mappedAction.setContact(member);

        Action addedAction = actionRepository.save(mappedAction);
        return modelMapper.map(addedAction, ActionResponse.class);
    }

    /**
     * Aktualisiert eine bestehende Aktion anhand der ID und der neuen Daten.
     *
     * @param id     ID der zu aktualisierenden Aktion
     * @param action DTO mit den neuen Werten
     * @return Aktualisierte Aktion als {@link ActionResponse}
     * @throws RuntimeException Wenn Aktion oder Mitglied nicht gefunden werden
     */
    public ActionResponse updateAction(long id, ActionDto action) {
        Action existing = actionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aktion wurde nicht gefunden!"));

        if (action.getYear() != null) {
            existing.setYear(action.getYear());
        }

        if (action.getDescription() != null) {
            existing.setDescription(action.getDescription());
        }

        if (action.getContactId() != null) {
            Member member = memberRepository.findById(action.getContactId())
                    .orElseThrow(() -> new RuntimeException("Mitglied nicht gefunden"));
            existing.setContact(member);
        }

        Action updatedAction = actionRepository.save(existing);
        return modelMapper.map(updatedAction, ActionResponse.class);
    }


    /**
     * Löscht eine bestehende Aktion anhand der ID.
     *
     * @param id     ID der zu aktualisierenden Aktion
     */
    public void deleteAction(long id) {
        actionRepository.deleteById(id);
    }

}
