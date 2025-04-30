package de.dlsa.api.controllers;

import de.dlsa.api.responses.*;
import de.dlsa.api.services.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(
            StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("statistics/membercount")
    public ResponseEntity<MemberCountResponse> getMemberCount() {
        MemberCountResponse result = statisticsService.getMemberCount();
        return ResponseEntity.ok(result);
    }

    @GetMapping("statistics/annualservicehours")
    public ResponseEntity<AnnualServiceHoursResponse> getAnnualServiceHours() {
        AnnualServiceHoursResponse result = statisticsService.getAnnualServiceHours();
        return ResponseEntity.ok(result);
    }

    @GetMapping("statistics/monthlyservicehours")
    public ResponseEntity<List<MonthlyServiceHoursResponse>> getMonthlyServiceHours() {
        List<MonthlyServiceHoursResponse> result = statisticsService.getMonthlyServiceHours();
        return ResponseEntity.ok(result);
    }

    @GetMapping("statistics/topdlsmember")
    public ResponseEntity<List<TopDlsMemberResponse>> getTopDlsMember() {
        List<TopDlsMemberResponse> result = statisticsService.getTopDlsMember();
        return ResponseEntity.ok(result);
    }

    @GetMapping("statistics/sectorswithdlsfromyear")
    public ResponseEntity<SelectedWithDlsFromYearResponse> getSelectedWithDlsFromYear(@RequestParam String code) {
        SelectedWithDlsFromYearResponse result = statisticsService.getSelectedWithDlsFromYear(code);
        return ResponseEntity.ok(result);
    }
}