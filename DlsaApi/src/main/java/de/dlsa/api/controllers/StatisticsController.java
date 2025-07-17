package de.dlsa.api.controllers;

import de.dlsa.api.responses.*;
import de.dlsa.api.services.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Verarbeitung von Anfragen zur statistischen Auswertung von Mitgliedsdaten und Dienstleistungsstunden.
 *
 * Bereitstellung von Kennzahlen und aggregierten Daten für Monitoring und Analysezwecke.
 *
 * Alle Endpunkte sind öffentlich zugänglich (kein Rollen-Check).
 *
 * Basis-URL: /statistics
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    /**
     * Konstruktor
     *
     * @param statisticsService Service zur Ermittlung statistischer Kennzahlen
     */
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * Liefert die Gesamtanzahl aller Mitglieder
     *
     * @return Anzahl der Mitglieder als {@link MemberCountResponse}
     */
    @GetMapping("statistics/membercount")
    public ResponseEntity<MemberCountResponse> getMemberCount() {
        MemberCountResponse result = statisticsService.getMemberCount();
        return ResponseEntity.ok(result);
    }

    /**
     * Liefert die insgesamt geleisteten Dienstleistungsstunden pro Jahr
     *
     * @return Dienstleistungsstunden pro Jahr als {@link AnnualServiceHoursResponse}
     */
    @GetMapping("statistics/annualservicehours")
    public ResponseEntity<AnnualServiceHoursResponse> getAnnualServiceHours() {
        AnnualServiceHoursResponse result = statisticsService.getAnnualServiceHours();
        return ResponseEntity.ok(result);
    }

    /**
     * Liefert die monatlich geleisteten Dienstleistungsstunden
     *
     * @return Liste mit Dienstleistungsstunden pro Monat als {@link MonthlyServiceHoursResponse}
     */
    @GetMapping("statistics/monthlyservicehours")
    public ResponseEntity<List<MonthlyServiceHoursResponse>> getMonthlyServiceHours() {
        List<MonthlyServiceHoursResponse> result = statisticsService.getMonthlyServiceHours();
        return ResponseEntity.ok(result);
    }

    /**
     * Liefert eine Liste der Mitglieder mit den höchsten Dienstleistungsstunden
     *
     * @return Top-Mitgliederliste als {@link TopDlsMemberResponse}
     */
    @GetMapping("statistics/topdlsmember")
    public ResponseEntity<List<TopDlsMemberResponse>> getTopDlsMember() {
        List<TopDlsMemberResponse> result = statisticsService.getTopDlsMember();
        return ResponseEntity.ok(result);
    }

    /**
     * Liefert Sektoren, die im angegebenen Jahr Dienstleistungsstunden erbracht haben
     *
     * @param code Jahrescode (z. B. "2024")
     * @return Ergebnisse als {@link SelectedWithDlsFromYearResponse}
     */
    @GetMapping("statistics/sectorswithdlsfromyear")
    public ResponseEntity<SelectedWithDlsFromYearResponse> getSelectedWithDlsFromYear(@RequestParam String code) {
        SelectedWithDlsFromYearResponse result = statisticsService.getSelectedWithDlsFromYear(code);
        return ResponseEntity.ok(result);
    }
}
