package de.dlsa.api.services;

import de.dlsa.api.dtos.ActionDto;
import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.ActionRepository;
import de.dlsa.api.repositories.MemberRepository;
import de.dlsa.api.responses.ActionResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActionService {

    private final ActionRepository actionRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public ActionService(
            ActionRepository actionRepository,
            MemberRepository memberRepository,
            ModelMapper modelMapper) {
        this.actionRepository = actionRepository;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    public List<ActionResponse> getActions() {
        return actionRepository.findAll().stream()
                .sorted(Comparator.comparingLong(Action::getId))
                .map(category -> modelMapper.map(category, ActionResponse.class))
                .collect(Collectors.toList());
    }

    public List<ActionResponse> createActions(List<ActionDto> actions) {

        List<Action> newAction = new ArrayList<>();

        for (ActionDto action: actions) {

            Action mappedAction = modelMapper.map(action, Action.class);

            Member member = memberRepository.findByMemberId(action.getContactId())
                    .orElseThrow(() -> new RuntimeException("Mitglied nicht gefunden"));
            mappedAction.setContact(member);

            newAction.add(mappedAction);
        }

        List<Action> addedActions = actionRepository.saveAll(newAction);

        return addedActions.stream()
                .sorted(Comparator.comparingLong(Action::getId))
                .map(action -> modelMapper.map(action, ActionResponse.class))
                .collect(Collectors.toList());
    }

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

            Member member = memberRepository.findByMemberId(action.getContactId())
                    .orElseThrow(() -> new RuntimeException("Mitglied nicht gefunden"));
            existing.setContact(member);
        }

        Action updatedAction =  actionRepository.save(existing);

        return modelMapper.map(updatedAction, ActionResponse.class);

    }


}
