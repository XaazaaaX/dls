package de.dlsa.test;

import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.*;
import de.dlsa.api.responses.EvaluationResponse;
import de.dlsa.api.responses.YearResponse;
import de.dlsa.api.services.EvaluationService;
import de.dlsa.api.services.SettingsService;
import de.dlsa.api.shared.CsvExporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluationServiceTest {

    @InjectMocks
    private EvaluationService evaluationService;
    @Mock private BookingRepository bookingRepository;
    @Mock private GroupRepository groupRepository;
    @Mock private MemberRepository memberRepository;
    @Mock private MemberChangesRepository memberChangesRepository;
    @Mock private SettingsService settingsService;
    @Mock private YearRepository yearRepository;
    @Mock private CourseOfYearRepository courseOfyearRepository;
    @Mock private BasicMemberRepository basicMemberRepository;
    @Mock private BasicGroupRepository basicGroupRepository;
    @Mock private GroupChangesRepository groupChangesRepository;
    @Mock private CsvExporter csvExporter;
    @Mock private ModelMapper modelMapper;

    private Member member;
    private Settings settings;

    @BeforeEach
    void setup() {
        member = new Member().setForename("Max").setSurname("Mustermann");
        settings = new Settings().setCostDls(10.0).setCountDls(24.0).setAgeFrom(18).setAgeTo(65).setBookingMethod("Volles Jahr zum Stichtag");
    }

    @Test
    void testGetYearsSortedDescending() {
        Year y2023 = new Year().setYear(2023);
        Year y2022 = new Year().setYear(2022);

        when(yearRepository.findAll()).thenReturn(List.of(y2022, y2023));
        when(modelMapper.map(any(), eq(YearResponse.class))).thenAnswer(inv -> {
            Year y = inv.getArgument(0);
            YearResponse yr = new YearResponse();
            yr.setYear(y.getYear());
            return yr;
        });

        List<YearResponse> result = evaluationService.getYears();

        assertEquals(2, result.size());
        assertEquals(2023, result.get(0).getYear());
        assertEquals(2022, result.get(1).getYear());
    }

    @Test
    void testGetFullDlsMonths_ExactRange() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 12, 31);

        int months = evaluationService.getFullDlsMonths(from, to);

        assertEquals(12, months);
    }

    @Test
    void testGetFullDlsMonths_StartMidMonth() {
        LocalDate from = LocalDate.of(2024, 1, 15);
        LocalDate to = LocalDate.of(2024, 12, 31);

        int months = evaluationService.getFullDlsMonths(from, to);

        assertEquals(11, months); // Startet ab Februar
    }

    @Test
    void testGetRequiredDls_Rounded() {
        double result = evaluationService.getRequiredDls(5, settings);
        assertEquals(10.0, result);
    }

    @Test
    void testGetMemberDebit_WithDebt() {
        double debit = evaluationService.getMemberDebit(12.0, 6.0, settings);
        assertEquals(60.0, debit);
    }

    @Test
    void testGetMemberDebit_NoDebt() {
        double debit = evaluationService.getMemberDebit(5.0, 10.0, settings);
        assertEquals(0.0, debit);
    }

    @Test
    void testIsPeriodOver_True() {
        LocalDate past = LocalDate.now().minusDays(1);
        assertTrue(evaluationService.isPeriodOver(past));
    }

    @Test
    void testIsPeriodOver_False() {
        LocalDate future = LocalDate.now().plusDays(1);
        assertFalse(evaluationService.isPeriodOver(future));
    }

    @Test
    void testEscapeCsvValue_WithSpecialChars() {
        String result = CsvExporter.escape("Hallo, \"Welt\"");
        assertEquals("\"Hallo, \"\"Welt\"\"\"", result);
    }

    @Test
    void testEscapeCsvValue_Normal() {
        String result =  CsvExporter.escape("NormalerText");
        assertEquals("NormalerText", result);
    }

    @Test
    void testGetAchievedDls_CallsRepository() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 12, 31);

        when(bookingRepository.getSumDlsByMemberAndDateRange(any(), any(), any())).thenReturn(8.0);

        double result = evaluationService.getAchievedDls(member, from, to);
        assertEquals(8.0, result);
    }

    @Test
    void testValidateYear_Found() {
        when(yearRepository.findByYear(2024)).thenReturn(java.util.Optional.of(new Year()));
        assertDoesNotThrow(() -> evaluationService.validateYear(2024));
    }

    @Test
    void testValidateYear_NotFound() {
        when(yearRepository.findByYear(2024)).thenReturn(java.util.Optional.empty());
        assertThrows(RuntimeException.class, () -> evaluationService.validateYear(2024));
    }

    @Test
    void testGetMemberLiberated_TooYoung() {
        // Mitglied ist 10 Jahre alt und zu jung für DLS
        member.setBirthdate(LocalDate.now().minusYears(10).atStartOfDay());

        settings.setAgeFrom(18);
        settings.setAgeTo(65);

        boolean result = evaluationService.getMemberLiberated(
                member,
                LocalDate.now().minusYears(1),
                LocalDate.now(),
                LocalDate.now(),
                settings
        );

        assertTrue(result); // Erwartet: befreit, weil zu jung
    }

    @Test
    void testGetMemberLiberated_TooOld() {
        Member member = new Member();
        // Geburt vor 80 Jahren, als LocalDateTime gesetzt
        member.setBirthdate(LocalDate.now().minusYears(80).atStartOfDay());

        Settings settings = new Settings()
                .setAgeFrom(18)
                .setAgeTo(65); // Altersgrenze z. B. 65 Jahre

        LocalDate toDate = LocalDate.of(2024, 12, 31);
        LocalDate fromDate = toDate.minusYears(1).plusDays(1);
        LocalDate currentMonthLastDay = toDate;

        boolean result = evaluationService.getMemberLiberated(
                member,
                fromDate,
                toDate,
                currentMonthLastDay,
                settings
        );

        assertTrue(result); // Erwartet: Befreit wegen zu hohen Alters
    }

    @Test
    void testGetMemberLiberated_ActiveInLiberatedGroup() {
        Group g = new Group().setLiberated(true);
        member.setGroups(List.of(g));
        boolean result = evaluationService.getMemberLiberated(member, LocalDate.now().minusYears(1), LocalDate.now(), LocalDate.now(), settings);
        assertTrue(result);
    }

    @Test
    void testGenerateCsvBytes_ValidInput() throws IOException {
        EvaluationResponse er = new EvaluationResponse()
                .setFirstName("Max")
                .setLastName("Mustermann")
                .setRequiredMonths(12)
                .setRequiredDls(24.0)
                .setAchievedDls(20.0)
                .setCostPerDls(10.0)
                .setToPay(40.0)
                .setComment("Test");

        byte[] csv = evaluationService.generateCsvBytes(List.of(er));
        String content = new String(csv);
        assertTrue(content.contains("Max;Mustermann;12"));
    }

    @Test
    void testGetRequiredDls_Rounding() {
        Settings settings = new Settings().setCountDls(25.0); // z. B. 25h/Jahr

        double required = evaluationService.getRequiredDls(5, settings); // 5 Monate → ~10.4h
        assertEquals(10.4, required);
    }

    @Test
    void testGetMemberDebit_NoDebitWhenOverfulfilled() {
        Settings settings = new Settings().setCostDls(10.0);

        double debit = evaluationService.getMemberDebit(10.0, 15.0, settings); // 5h zu viel
        assertEquals(0.0, debit); // Kein "Guthaben", aber auch kein Minus
    }

    @Test
    void testIsBeforeOrInLastCoy_EqualDates() {
        LocalDate lastDueDate = LocalDate.of(2023, 12, 31);
        LocalDate toDate = LocalDate.of(2023, 12, 31);

        boolean result = evaluationService.isBeforeOrInLastCoy(lastDueDate, toDate);
        assertTrue(result); // Gleiches Datum, zählt als "in letzter COY"
    }
}