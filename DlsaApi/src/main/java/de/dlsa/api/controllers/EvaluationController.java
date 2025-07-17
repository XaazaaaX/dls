package de.dlsa.api.controllers;

import de.dlsa.api.exceptions.CoyFailedException;
import de.dlsa.api.responses.CourseOfYearResponse;
import de.dlsa.api.responses.EvaluationResponse;
import de.dlsa.api.responses.YearResponse;
import de.dlsa.api.services.EvaluationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Verarbeitung von Anfragen zur Durchführung und Auswertung von Jahres-Evaluationen
 * sowie zum Export der Ergebnisse im CSV-Format.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RestController
public class EvaluationController {

    private final EvaluationService evaluationService;

    /**
     * Konstruktor
     *
     * @param evaluationService Service zur Verarbeitung von Evaluationen
     */
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    /**
     * Endpunkt zur Abfrage aller verfügbaren Evaluationsjahre
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @return Liste von Jahren, für die Evaluationen vorliegen
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("evaluations/years")
    public ResponseEntity<List<YearResponse>> getYears() {
        List<YearResponse> result = evaluationService.getYears();
        return ResponseEntity.ok(result);
    }

    /**
     * Endpunkt zur Abfrage aller Evaluationsergebnisse gruppiert nach Kursen und Jahren
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @return Liste von Evaluationsergebnissen je Kurs und Jahr
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/evaluations")
    public ResponseEntity<List<CourseOfYearResponse>> getAllEvaluations() {
        List<CourseOfYearResponse> result = evaluationService.getEvaluations();
        return ResponseEntity.ok(result);
    }

    /**
     * Endpunkt zur Durchführung der jährlichen Evaluation (Course of Year – COY)
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param year     Jahr der durchzuführenden Evaluation
     * @param finalize Gibt an, ob die Ergebnisse final gespeichert werden sollen
     * @return Liste der Evaluationsergebnisse
     * @throws IOException           bei Dateioperationen
     * @throws CoyFailedException   bei Fehlern im Evaluationsprozess
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/evaluation")
    public ResponseEntity<List<EvaluationResponse>> doAnnualEvaluation(
            @RequestParam int year,
            @RequestParam boolean finalize
    ) throws IOException, CoyFailedException {
        List<EvaluationResponse> result = evaluationService.runCoy(year, finalize);
        return ResponseEntity.ok(result);
    }

    /**
     * Endpunkt zum Export der jährlichen Evaluationsergebnisse als CSV-Datei
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param year     Jahr der Evaluation
     * @param finalize Gibt an, ob die Ergebnisse final gespeichert werden sollen
     * @param response HTTP-Response zum Schreiben der CSV-Daten
     * @throws IOException           bei Dateioperationen
     * @throws CoyFailedException   bei Fehlern im Evaluationsprozess
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping(value = "/evaluation/export", produces = "text/csv")
    public void doAnnualEvaluationExportCsv(
            @RequestParam int year,
            @RequestParam boolean finalize,
            HttpServletResponse response
    ) throws IOException, CoyFailedException {
        evaluationService.runCoy(year, finalize, response);
    }
}
