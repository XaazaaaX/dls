package de.dlsa.api.services;

import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.*;
import de.dlsa.api.shared.CourseOfYearResult;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseOfYearService {

    private final BookingRepository bookingRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final MemberChangesRepository memberChangesRepository;
    private final SettingsService settingsService;
    private final YearRepository yearRepository;
    private final CourseOfyearRepository courseOfyearRepository;
    private final BasicMemberRepository basicMemberRepository;
    private final BasicGroupRepository basicGroupRepository;
    private final GroupChangesRepository groupChangesRepository;
    private final ModelMapper modelMapper;

    public CourseOfYearService(GroupRepository groupRepository,
                               BookingRepository bookingRepository,
                               MemberRepository memberRepository,
                               MemberChangesRepository memberChangesRepository,
                               YearRepository yearRepository,
                               SettingsService settingsService,
                               BasicGroupRepository basicGroupRepository,
                               CourseOfyearRepository courseOfyearRepository,
                               BasicMemberRepository basicMemberRepository,
                               GroupChangesRepository groupChangesRepository,
                               ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.bookingRepository = bookingRepository;
        this.memberRepository = memberRepository;
        this.memberChangesRepository = memberChangesRepository;
        this.yearRepository = yearRepository;
        this.settingsService = settingsService;
        this.courseOfyearRepository = courseOfyearRepository;
        this.basicMemberRepository = basicMemberRepository;
        this.basicGroupRepository = basicGroupRepository;
        this.groupChangesRepository = groupChangesRepository;
        this.modelMapper = modelMapper;
    }

    public List<CourseOfYearResult> calculateForYear(int year, boolean finalize) {

        // Prüfen ob schon ein final gemacht wurde


        Settings settings = settingsService.getSettings();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        yearRepository.findByYear(year).orElseThrow(() -> new RuntimeException("Es liegen keine Buchungen für das übermittelte Jahr vor!"));

        LocalDate lastDueDate = null;
        LocalDateTime lastDoneTimestamp = courseOfyearRepository.findLastDoneDate();
        if (lastDoneTimestamp != null) {
            lastDueDate = lastDoneTimestamp.toLocalDate();
        }

        LocalDate toDate = LocalDate.parse(settings.getDueDate() + "." + year, formatter);
        LocalDate fromDate = LocalDate.parse(settings.getDueDate() + "." + year, formatter)
                .minusYears(1)
                .plusDays(1);

        // Did the due date change since the last course of year?
        if (lastDueDate != null && fromDate.isBefore(lastDueDate)) {
            fromDate = lastDueDate.plusDays(1);
        }

        // LOG
        System.out.println("-------------------------------------------");
        System.out.println("Debug Jahreslauf " + year);
        System.out.println("Von: " + fromDate.toString());
        System.out.println("Bis: " + toDate.toString());
        System.out.println("-------------------------------------------");

        // Date Validation:
        boolean isPeriodOver = LocalDate.now().isAfter(toDate);
        boolean isBeforeOrInLastCoy = lastDueDate != null && (toDate.isBefore(lastDueDate) || toDate.isEqual(lastDueDate));

        if (isPeriodOver) {
            if (isBeforeOrInLastCoy) {
                throw new RuntimeException("Der angegebene Zeitraum liegt vor oder in einem abgeschlossenem Jahreslauf, bitte das Ergebnis in der Historie verwenden!");
            }
        } else {
            throw new RuntimeException("Der Jahreslauf kann erst gestartet werden, wenn der Zeitraum beendet ist!");
        }

        //List<Member> members = memberRepository.findActiveWithAikzTrue();
        List<Member> members = memberRepository.findAll();
        List<CourseOfYearResult> results = new ArrayList<>();

        for (Member member : members) {
            CourseOfYearResult result = calculateForMember(member, fromDate, toDate, settings, finalize);
            results.add(result);
        }

        return results;
    }

    private CourseOfYearResult calculateForMember(Member member, LocalDate fromDate, LocalDate toDate, Settings settings, boolean finalize) {

        int requiredMonths;

        if (settings.getBookingMethod().equals("Anteilig bis zum Stichtag")){

            // Das ist die Anzhal an Monaten die nicht DLS bereit sind
            requiredMonths = getRequiredMonths(fromDate, toDate, member, settings);

        } else {
            // Settings: Volles Jahr zum Stichtag

            // Das ist die Anzhal an Monaten die nicht DLS bereit sind
            requiredMonths = getFullDlsMonths(fromDate, toDate);

        }

        // Das sind die Anzhal an der Stunden die erbrahct werden müssen
        double requiredDls = getRequiredDls(requiredMonths, settings);

        // Das sind die Anzhal an der Stunden die bereits geleistet wurden
        double achievedDls = getAchievedDls(member, fromDate, toDate);

        // Kosten für nicht geleistete DLS
        double memberDebit = getMemberDebit(requiredDls, achievedDls, settings);

        CourseOfYearResult result = new CourseOfYearResult();
        result.firstName = member.getForename();
        result.lastName = member.getSurname();
        result.requiredMonths = requiredMonths;
        result.requiredDls = requiredDls;
        result.achievedDls = achievedDls;
        result.costPerDls = settings.getCostDls();
        result.toPay = memberDebit;


        return result;
    }

    public int getFullDlsMonths(LocalDate fromDate, LocalDate toDate) {
        /*if (fromDate == null || toDate == null || fromDate.isAfter(toDate)) {
            return 0;
        }

        return (int) ChronoUnit.MONTHS.between(fromDate, toDate);

         */

        int monthCount = 0;

        // Starte ab dem nächsten Monat, falls fromDate nicht am 1. ist
        LocalDate currentMonth = fromDate.getDayOfMonth() == 1
                ? fromDate
                : fromDate.plusMonths(1).withDayOfMonth(1);

        while (!currentMonth.isAfter(toDate)) {
            monthCount++;

            currentMonth = currentMonth.plusMonths(1);
        }

        return monthCount;
    }

    public int getRequiredMonths(LocalDate fromDate, LocalDate toDate, Member member, Settings settings) {
        int monthCount = 0;

        // Starte ab dem nächsten Monat, falls fromDate nicht am 1. ist
        LocalDate currentMonth = fromDate.getDayOfMonth() == 1
                ? fromDate
                : fromDate.plusMonths(1).withDayOfMonth(1);

        while (!currentMonth.isAfter(toDate)) {
            LocalDate currentMonthLastDay = currentMonth.plusMonths(1).minusDays(1);
            Member stateMember = getMemberStateFromDate(member, currentMonthLastDay);

            if (!getMemberLiberated(stateMember, fromDate, toDate, currentMonthLastDay, settings)) {
                monthCount++;
            }

            currentMonth = currentMonth.plusMonths(1);
        }

        return monthCount;
    }


    public Member getMemberStateFromDate(Member member, LocalDate date) {

        BasicMember bMember = basicMemberRepository.findBasicMemberByMember(member);

        Member reconstructedMember = member;
        reconstructedMember
                .setEntryDate(bMember.getEntryDate())
                .setLeavingDate(bMember.getLeavingDate())
                .setActive(bMember.getActive())
                .setGroups(null);

        List<MemberChanges> changes = memberChangesRepository.findChangesUpToDate(member.getId(), date.atStartOfDay());

        for (MemberChanges change : changes) {
            switch (change.getColumn()) {
                case "ENTRYDATE":
                    reconstructedMember.setEntryDate(LocalDateTime.parse(change.getNewValue()));
                    break;
                case "LEAVINGDATE":
                    reconstructedMember.setLeavingDate(LocalDateTime.parse(change.getNewValue()));
                    break;
                case "ACTIVE":
                    reconstructedMember.setActive(Boolean.parseBoolean(change.getNewValue()));
                    break;
                case "GROUP":
                    Iterable<Long> groupIds = Arrays.stream(change.getNewValue().split(" "))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());

                    List<Group> groups = groupRepository.findAllById(groupIds);

                    reconstructedMember.setGroups(groups);
                    break;
            }
        }

        return reconstructedMember;
    }

    public boolean getMemberLiberated(Member member, LocalDate fromDate, LocalDate toDate, LocalDate currentMonthLastDay, Settings settings) {
        // Member is not active at the moment
        if (member.getActive() != null && !member.getActive()) {
            return true;
        }
        // Member is too Young
        if (member.getAge(toDate.atStartOfDay()) != -1 && member.getAge(toDate.atStartOfDay()) < settings.getAgeFrom()) {
            return true;
        }
        // Member is too old
        if (member.getAge(fromDate.atStartOfDay()) != -1 && member.getAge(fromDate.atStartOfDay()) >= settings.getAgeTo()) {
            return true;
        }
        // Entry date is after the due date
        if (member.getEntryDate() != null && member.getEntryDate().isAfter(toDate.atStartOfDay())) {
            return true;
        }
        // Leaving date is before the due date
        if ( member.getLeavingDate() != null && member.getLeavingDate().isBefore(fromDate.atStartOfDay()) ) {
            return true;
        }
        // Member is part of a group which liberates him at the moment
        if(member.getGroups() != null){
            for (Group group : member.getGroups()) {

                if (getGroupStateFromDate(group, currentMonthLastDay).getLiberated()) {
                    return true;
                }
            }
        }

        // Default is false
        return false;
    }

    public Group getGroupStateFromDate(Group group, LocalDate date) {

        BasicGroup bGroup = basicGroupRepository.findBasicGroupByGroup(group);

        Group reconstructedGroup = group;
        reconstructedGroup
                .setLiberated(bGroup.getLiberate());

        List<GroupChanges> changes = groupChangesRepository.findChangesUpToDate(group.getId(), date.atStartOfDay());

        for (GroupChanges change : changes) {
            reconstructedGroup.setLiberated(change.getNewValue());
        }

        return reconstructedGroup;
    }

    public double getRequiredDls(int requieredMonths, Settings settings) {
        return (((double) Math.round(settings.getCountDls() / 12 * requieredMonths * 10)) / 10);
    }

    public double getAchievedDls(Member member, LocalDate fromDate, LocalDate toDate) {
        return bookingRepository.getSumDlsByMemberAndDateRange(member, fromDate.atStartOfDay(), toDate.atTime(LocalTime.MAX));
    }

    public double getMemberDebit(double requiredDls, double achievedDls, Settings settings) {
        double sum = Math.round((requiredDls - achievedDls) * 10.0) / 10.0;
        // If the sum is lower than 0, the member has no debt
        if (sum < 0) {
            return 0;
        }
        return Math.round((sum * settings.getCostDls()) * 100.0) / 100.0;
    }
}


