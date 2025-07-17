package de.dlsa.api.services;

import de.dlsa.api.entities.Settings;
import de.dlsa.api.repositories.*;
import de.dlsa.api.responses.*;
import de.dlsa.api.shared.CsvExporter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviceklasse zur Bereitstellung statistischer Auswertungen über Dienstleistungsstunden,
 * Mitgliederverteilung und Gruppierungen über verschiedene Zeiträume hinweg.
 */
@Service
public class StatisticsService {

    // Repositories und Hilfsklassen
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
    private final SectorRepository sectorRepository;
    private final CsvExporter csvExporter;
    private final ModelMapper modelMapper;

    /**
     * Konstruktor zur Initialisierung des {@link StatisticsService}.
     */
    public StatisticsService(GroupRepository groupRepository,
                             BookingRepository bookingRepository,
                             MemberRepository memberRepository,
                             MemberChangesRepository memberChangesRepository,
                             YearRepository yearRepository,
                             SettingsService settingsService,
                             BasicGroupRepository basicGroupRepository,
                             CourseOfYearRepository courseOfyearRepository,
                             BasicMemberRepository basicMemberRepository,
                             GroupChangesRepository groupChangesRepository,
                             SectorRepository sectorRepository,
                             CsvExporter csvExporter,
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
        this.sectorRepository = sectorRepository;
        this.csvExporter = csvExporter;
        this.modelMapper = modelMapper;
    }

    /**
     * Zählt Mitglieder insgesamt, aktiv, passiv und im DLS-pflichtigen Alter.
     *
     * @return {@link MemberCountResponse} mit den vier Kennzahlen
     */
    public MemberCountResponse getMemberCount() {
        long allMembers = memberRepository.countAllMembers();
        long activeMembers = memberRepository.countActiveMembers();
        long passiveMembers = memberRepository.countPassiveMembers();

        Settings settings = settingsService.getSettings();
        LocalDateTime start = LocalDate.now().minusYears(settings.getAgeFrom()).atStartOfDay();
        LocalDateTime end = LocalDate.now().minusYears(settings.getAgeTo()).atTime(LocalTime.MAX);
        long dlsRequiredMembers = memberRepository.countMembersBornBetween(start, end);

        return new MemberCountResponse()
                .setAllMembers(allMembers)
                .setActiveMembers(activeMembers)
                .setPassiveMembers(passiveMembers)
                .setDlsRequiredMembers(dlsRequiredMembers);
    }

    /**
     * Liefert eine Statistik der jährlich geleisteten Dienstleistungsstunden der letzten 10 Jahre.
     *
     * @return {@link AnnualServiceHoursResponse} mit Labels (Jahre) und Summen der Stunden
     */
    public AnnualServiceHoursResponse getAnnualServiceHours() {
        int currentYear = Year.now().getValue();
        int startYear = currentYear - 9;

        Map<Integer, Double> yearToHours = new LinkedHashMap<>();
        for (int year = startYear; year <= currentYear; year++) {
            yearToHours.put(year, 0.0);
        }

        List<Object[]> results = bookingRepository.findServiceHoursPerYearFrom(startYear);
        for (Object[] row : results) {
            Integer year = (Integer) row[0];
            Double sum = (Double) row[1];
            yearToHours.put(year, sum);
        }

        List<String> labels = yearToHours.keySet().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        List<Double> data = new ArrayList<>(yearToHours.values());

        return new AnnualServiceHoursResponse()
                .setLabels(labels)
                .setData(data);
    }

    /**
     * Gibt eine Monatsübersicht der Dienstleistungsstunden für die letzten drei Jahre.
     *
     * @return Liste von {@link MonthlyServiceHoursResponse}, je Jahr eine Zeile
     */
    public List<MonthlyServiceHoursResponse> getMonthlyServiceHours() {
        int currentYear = Year.now().getValue();
        int startYear = currentYear - 2;

        Map<Integer, Map<Integer, Double>> yearMonthToHours = new LinkedHashMap<>();
        for (int year = startYear; year <= currentYear; year++) {
            Map<Integer, Double> monthToHours = new LinkedHashMap<>();
            for (int month = 1; month <= 12; month++) {
                monthToHours.put(month, 0.0);
            }
            yearMonthToHours.put(year, monthToHours);
        }

        LocalDateTime startDate = LocalDate.of(startYear, 1, 1).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(currentYear, 12, 31).atTime(LocalTime.MAX);
        List<Object[]> results = bookingRepository.findMonthlyServiceHours(startDate, endDate);

        for (Object[] row : results) {
            Integer year = ((Number) row[0]).intValue();
            Integer month = ((Number) row[1]).intValue();
            Double totalDls = ((Number) row[2]).doubleValue();
            yearMonthToHours.get(year).put(month, totalDls);
        }

        List<MonthlyServiceHoursResponse> responses = new ArrayList<>();
        for (int year = currentYear; year >= startYear; year--) {
            MonthlyServiceHoursResponse response = new MonthlyServiceHoursResponse();
            response.setLabel(String.valueOf(year));
            List<Double> data = new ArrayList<>();
            for (int month = 1; month <= 12; month++) {
                data.add(yearMonthToHours.get(year).get(month));
            }
            response.setData(data);
            responses.add(response);
        }

        return responses;
    }

    /**
     * Liefert die Top 5 Mitglieder mit den meisten DLS-Stunden insgesamt.
     *
     * @return Liste von {@link TopDlsMemberResponse} mit Namen und geleisteten Stunden
     */
    public List<TopDlsMemberResponse> getTopDlsMember() {
        List<Object[]> results = bookingRepository.findTop5DlsMembers();

        return results.stream()
                .map(row -> {
                    String fullName = row[0] + " " + row[1];
                    Double dls = ((Number) row[2]).doubleValue();
                    return new TopDlsMemberResponse().setLabel(fullName).setData(dls);
                })
                .collect(Collectors.toList());
    }

    /**
     * Zeigt die geleisteten DLS-Stunden über drei Jahre für Gruppen, Sparten oder Bereiche.
     *
     * @param code Auswahlparameter: "group", "category" oder beliebig (default: sector)
     * @return {@link SelectedWithDlsFromYearResponse} mit Labels und DLS-Werten pro Jahr
     */
    public SelectedWithDlsFromYearResponse getSelectedWithDlsFromYear(String code) {
        int currentYear = Year.now().getValue();
        Set<String> labels = new HashSet<>();
        List<SelectedWithDlsFromYearBodyResponse> body = new ArrayList<>();
        List<Object[]> results;

        for (int i = 0; i < 3; i++) {
            switch (code) {
                case "group":
                    results = bookingRepository.findGroupsWithDlsFromYear(currentYear - i);
                    break;
                case "category":
                    results = bookingRepository.findCategoriesWithDlsFromYear(currentYear - i);
                    break;
                default:
                    results = bookingRepository.findSectorsWithDlsFromYear(currentYear - i);
                    break;
            }

            List<Double> data = new ArrayList<>();
            for (Object[] row : results) {
                labels.add((String) row[0]);
                data.add((Double) row[1]);
            }

            SelectedWithDlsFromYearBodyResponse item = new SelectedWithDlsFromYearBodyResponse()
                    .setLabel(String.valueOf(currentYear - i))
                    .setData(data);

            body.add(item);
        }

        return new SelectedWithDlsFromYearResponse()
                .setLabels(labels.stream().sorted().collect(Collectors.toList()))
                .setBody(body);
    }
}
