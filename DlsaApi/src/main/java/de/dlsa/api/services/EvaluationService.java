package de.dlsa.api.services;

import de.dlsa.api.entities.*;
import de.dlsa.api.exceptions.CoyFailedException;
import de.dlsa.api.repositories.*;
import de.dlsa.api.responses.CourseOfYearResponse;
import de.dlsa.api.responses.EvaluationResponse;
import de.dlsa.api.responses.YearResponse;
import de.dlsa.api.shared.CsvExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service für die Durchführung des Jahreslaufs (Course of Year, COY) zur Berechnung
 * der Dienstleistungsstunden (DLS) für Mitglieder sowie zur optionalen Verbuchung
 * der Soll-/Ist-Differenz und Erstellung eines CSV-Exports.
 *
 * Die Berechnung berücksichtigt individuelle Befreiungen (Alter, Gruppenstatus, Aktivität etc.),
 * verwaltet historische Veränderungen an Gruppen-/Mitgliedsdaten und speichert abgeschlossene
 * Jahresläufe dauerhaft.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Service
public class EvaluationService {

    private final BookingRepository bookingRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final MemberChangesRepository memberChangesRepository;
    private final SettingsService settingsService;
    private final YearRepository yearRepository;
    private final CourseOfYearRepository courseOfyearRepository;
    private final BasicMemberRepository basicMemberRepository;
    private final BasicGroupRepository basicGroupRepository;
    private final GroupChangesRepository groupChangesRepository;
    private final CsvExporter csvExporter;
    private final ModelMapper modelMapper;

    public EvaluationService(GroupRepository groupRepository,
                             BookingRepository bookingRepository,
                             MemberRepository memberRepository,
                             MemberChangesRepository memberChangesRepository,
                             YearRepository yearRepository,
                             SettingsService settingsService,
                             BasicGroupRepository basicGroupRepository,
                             CourseOfYearRepository courseOfyearRepository,
                             BasicMemberRepository basicMemberRepository,
                             GroupChangesRepository groupChangesRepository,
                             CsvExporter csvExporter, ModelMapper modelMapper) {
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
        this.csvExporter = csvExporter;
        this.modelMapper = modelMapper;
    }

    public List<YearResponse> getYears() {
        List<Year> years = yearRepository.findAll();
        return years.stream()
                .sorted(Comparator.comparing(Year::getYear).reversed())
                .map(year -> modelMapper.map(year, YearResponse.class))
                .collect(Collectors.toList());
    }

    public List<CourseOfYearResponse> getEvaluations() {
        List<CourseOfYear> coys = courseOfyearRepository.findAll();
        return coys.stream()
                //.sorted(Comparator.comparing(Booking::getDoneDate).reversed())
                .map(coy -> modelMapper.map(coy, CourseOfYearResponse.class))
                .collect(Collectors.toList());
    }

    // Hauptfunktion Jahreslauf (ohne CSV-Export)
    public List<EvaluationResponse> runCoy(int year, boolean finalize) throws IOException, CoyFailedException {

        // Check if any booking exists
        validateYear(year);

        // get lastDueDate from last coy-run
        LocalDate lastDueDate = getLastDueDate();

        // load settings
        Settings settings = settingsService.getSettings();

        // set fromdate and duedate
        LocalDate toDate = getToDate(year, settings);
        LocalDate fromDate = getFromDate(year, settings, lastDueDate);

        // Log
        logFromToDates(year, fromDate, toDate);

        // Timespan Validation
        if (isPeriodOver(toDate)) {
            if (isBeforeOrInLastCoy(lastDueDate, toDate)) {
                throw new CoyFailedException("Der angegebene Zeitraum liegt vor oder in einem abgeschlossenem Jahreslauf, bitte das Ergebnis in der Historie verwenden!");
            }
        } else {
            throw new CoyFailedException("Der Jahreslauf kann erst gestartet werden, wenn der Zeitraum beendet ist!");
        }


        // Calculate for active and aikz member
        List<Member> members = memberRepository.findAll();
        List<EvaluationResponse> results = calculateForMemberList(members, fromDate, toDate, settings, finalize);


        // Save Coy
        if (finalize) {
            saveCourseOfYear(results, toDate);
        }

        return results;
    }

    public void validateYear(int year) {
        yearRepository.findByYear(year).orElseThrow(() -> new RuntimeException("Es liegen keine Buchungen für das übermittelte Jahr vor!"));
    }

    private LocalDate getLastDueDate() {
        LocalDateTime lastDoneTimestamp = courseOfyearRepository.findLastDoneDate();
        if (lastDoneTimestamp != null) {
            return lastDoneTimestamp.toLocalDate();
        }

        return null;
    }

    private LocalDate getToDate(int year, Settings settings) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(settings.getDueDate() + "." + year, formatter);
    }

    private LocalDate getFromDate(int year, Settings settings, LocalDate lastDueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate fromDate = LocalDate.parse(settings.getDueDate() + "." + year, formatter)
                .minusYears(1)
                .plusDays(1);

        // Did the due date change since the last course of year?
        if (lastDueDate != null && fromDate.isBefore(lastDueDate)) {
            return lastDueDate.plusDays(1);
        }

        return fromDate;
    }

    private void logFromToDates(int year, LocalDate from, LocalDate to) {
        System.out.println("-------------------------------------------");
        System.out.println("Debug Jahreslauf " + year);
        System.out.println("Von: " + from.toString());
        System.out.println("Bis: " + to.toString());
        System.out.println("-------------------------------------------");
    }

    public boolean isPeriodOver(LocalDate toDate) {
        return LocalDate.now().isAfter(toDate);
    }

    public boolean isBeforeOrInLastCoy(LocalDate lastDueDate, LocalDate toDate) {
        return lastDueDate != null && (toDate.isBefore(lastDueDate) || toDate.isEqual(lastDueDate));
    }

    private List<EvaluationResponse> calculateForMemberList(List<Member> members, LocalDate fromDate, LocalDate toDate, Settings settings, boolean finalize) {

        List<EvaluationResponse> results = new ArrayList<>();
        for (Member member : members) {
            EvaluationResponse result = calculateForMember(member, fromDate, toDate, settings, finalize);
            results.add(result);
        }
        return results;
    }

    private void saveCourseOfYear(List<EvaluationResponse> results, LocalDate toDate) throws IOException {
        CourseOfYear coy = new CourseOfYear()
                .setFile(generateCsvBytes(results))
                .setTimestamp(LocalDateTime.now())
                .setDisplayName("Jahreslauf vom " + toDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .setDueDate(toDate.atStartOfDay())
                .setFilename(toDate + "_Jahreslauf.csv");

        courseOfyearRepository.save(coy);
    }

    public void runCoy(int year, boolean finalize, HttpServletResponse response) throws IOException, CoyFailedException {

        // Check if any booking exists
        validateYear(year);

        // get lastDueDate from last coy-run
        LocalDate lastDueDate = getLastDueDate();

        // load settings
        Settings settings = settingsService.getSettings();

        // set fromdate and duedate
        LocalDate toDate = getToDate(year, settings);
        LocalDate fromDate = getFromDate(year, settings, lastDueDate);

        // Log
        logFromToDates(year, fromDate, toDate);

        // Timespan Validation
        if (isPeriodOver(toDate)) {
            if (isBeforeOrInLastCoy(lastDueDate, toDate)) {
                throw new CoyFailedException("Der angegebene Zeitraum liegt vor oder in einem abgeschlossenem Jahreslauf, bitte das Ergebnis in der Historie verwenden!");
            }
        } else {
            throw new CoyFailedException("Der Jahreslauf kann erst gestartet werden, wenn der Zeitraum beendet ist!");
        }


        // Calculate for active and aikz member
        List<Member> members = memberRepository.findAll();
        List<EvaluationResponse> results = calculateForMemberList(members, fromDate, toDate, settings, finalize);


        // Save Coy
        if (finalize) {
            saveCourseOfYear(results, toDate);
        }

        String filename = toDate + "_Jahreslauf.csv";

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        try (Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8)) {
            csvExporter.export(results, writer);
        }
    }

    public EvaluationResponse calculateForMember(Member member, LocalDate fromDate, LocalDate toDate, Settings settings, boolean finalize) {

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

        // Kommentare setzen
        String comment = "";
        if (member.getAge(toDate.atTime(LocalTime.MAX)) == -1) {
            comment += "Kein Geburtsdatum vorhanden.";
        }
        else if (requiredMonths > 0 && requiredMonths < 12) {
            if (requiredMonths == 1) {
                comment += "1 Monat DLS befreit";
            } else
                comment += 12 - requiredMonths + " Monate DLS befreit.";
        } else if (requiredMonths == 0) {
            comment += "Alle Monat DLS befreit.";
        } else if (requiredMonths == 12) {
            comment += "Keinen Monat DLS befreit.";
        }

        EvaluationResponse result = new EvaluationResponse()
                .setFirstName(member.getForename())
                .setLastName(member.getSurname())
                .setRequiredMonths(requiredMonths)
                .setRequiredDls(requiredDls)
                .setAchievedDls(achievedDls)
                .setCostPerDls(settings.getCostDls())
                .setToPay(memberDebit)
                .setComment(comment);

        // Buchungen
        if (finalize) {
            // Buchung der benötigten DLS
            Booking rbooking = new Booking()
                    .setMember(member)
                    .setComment("Buchung der benötigten DLS")
                    .setDoneDate(toDate.atStartOfDay())
                    .setCountDls(-requiredDls);
            bookingRepository.save(rbooking);

            // Ausgleichsbuchung, falls Clearing aktiviert ist
            if (settings.getClearing()) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                double diff = requiredDls - achievedDls;

                Booking abooking = new Booking()
                        .setMember(member)
                        .setComment("Ausgleichsbuchung Jahreslauf " + LocalDate.now().format(formatter))
                        .setDoneDate(toDate.atStartOfDay())
                        .setCountDls(diff);
                bookingRepository.save(abooking);
            }
        }


        return result;
    }

    public int getFullDlsMonths(LocalDate fromDate, LocalDate toDate) {
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
        if ( member.getLeavingDate() != null && member.getLeavingDate().isBefore(currentMonthLastDay.atTime(LocalTime.MAX)) ) {
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

    public byte[] generateCsvBytes(List<EvaluationResponse> results) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(byteStream, StandardCharsets.UTF_8);

        writer.write('\uFEFF'); // UTF-8 BOM
        writer.write("Vorname; Nachname; Monate mit DLS-Pflicht; Benötigte DLS; Geleistete DLS; Kosten Pro nicht geleisteter DLS; Zu Zahlen (in Euro); Bemerkung\n");

        for (EvaluationResponse result : results) {
            writer.write(String.format(
                    "%s;%s;%d;%.1f;%.1f;%.2f;%.2f;%s\n",
                    escape(result.getFirstName()),
                    escape(result.getLastName()),
                    result.getRequiredMonths(),
                    result.getRequiredDls(),
                    result.getAchievedDls(),
                    result.getCostPerDls(),
                    result.getToPay(),
                    escape(result.getComment())
            ));
        }

        writer.flush();
        return byteStream.toByteArray();
    }

    private static String escape(String value) {
        if (value == null) return "";
        // Falls Komma oder Anführungszeichen enthalten: escapen
        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }

}


