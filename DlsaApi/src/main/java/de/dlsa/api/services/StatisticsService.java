package de.dlsa.api.services;

import de.dlsa.api.entities.Sector;
import de.dlsa.api.entities.Settings;
import de.dlsa.api.repositories.*;
import de.dlsa.api.responses.*;
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
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@Service
public class StatisticsService {

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
        this.sectorRepository = sectorRepository;
        this.csvExporter = csvExporter;
        this.modelMapper = modelMapper;
    }

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

    public AnnualServiceHoursResponse getAnnualServiceHours() {

        int currentYear = Year.now().getValue();
        int startYear = currentYear - 9;

        // Initialize with zero for each year
        Map<Integer, Double> yearToHours = new LinkedHashMap<>();
        for (int year = startYear; year <= currentYear; year++) {
            yearToHours.put(year, 0.0);
        }

        // Get actual data from DB
        List<Object[]> results = bookingRepository.findServiceHoursPerYearFrom(startYear);
        for (Object[] row : results) {
            Integer year = (Integer) row[0];
            Double sum = (Double) row[1];
            yearToHours.put(year, sum);
        }

        // Convert to labels and data
        List<String> labels = yearToHours.keySet().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        List<Double> data = new ArrayList<>(yearToHours.values());

        return new AnnualServiceHoursResponse()
                .setLabels(labels)
                .setData(data);
    }

    public List<MonthlyServiceHoursResponse> getMonthlyServiceHours() {
        int currentYear = Year.now().getValue();
        int startYear = currentYear - 2; // 3 Jahre: inkl. currentYear

        // Initialisiere Map: year -> month -> hours
        Map<Integer, Map<Integer, Double>> yearMonthToHours = new LinkedHashMap<>();
        for (int year = startYear; year <= currentYear; year++) {
            Map<Integer, Double> monthToHours = new LinkedHashMap<>();
            for (int month = 1; month <= 12; month++) {
                monthToHours.put(month, 0.0);
            }
            yearMonthToHours.put(year, monthToHours);
        }

        // Hole Daten aus DB
        LocalDateTime startDate = LocalDate.of(startYear, 1, 1).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(currentYear, 12, 31).atTime(LocalTime.MAX);
        List<Object[]> results = bookingRepository.findMonthlyServiceHours(startDate, endDate);

        // Ergebnisse einspeisen
        for (Object[] row : results) {
            Integer year = ((Number) row[0]).intValue();
            Integer month = ((Number) row[1]).intValue();
            Double totalDls = ((Number) row[2]).doubleValue();
            yearMonthToHours.get(year).put(month, totalDls);
        }

        // Rückgabe vorbereiten
        List<MonthlyServiceHoursResponse> responses = new ArrayList<>();

        // Absteigend sortiert (neueste Linie zuerst)
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

    public SectorsWithDlsFromYearResponse getSectorsWithDlsFromYear() {

        int currentYear = Year.now().getValue();

        Set<String> labels = new HashSet<>();
        List<SectorsWithDlsFromYearBodyResponse> body = new ArrayList<SectorsWithDlsFromYearBodyResponse>();

        for (int i = 0; i < 3; i++) {
            List<Object[]> results = bookingRepository.findSectorsWithDlsFromYear(currentYear - i);

            List<Double> data = new ArrayList<>();

            for (Object[] row : results) {

                labels.add((String) row[0]);
                data.add((Double) row[1]);
            }

            SectorsWithDlsFromYearBodyResponse item = new SectorsWithDlsFromYearBodyResponse()
                    .setLabel(String.valueOf(currentYear - i))
                    .setData(data);

            body.add(item);
        }

        return new SectorsWithDlsFromYearResponse()
                .setLabels(labels.stream().toList().stream()
                        .sorted()
                        .collect(Collectors.toList()))
                .setBody(body);
    }

}


