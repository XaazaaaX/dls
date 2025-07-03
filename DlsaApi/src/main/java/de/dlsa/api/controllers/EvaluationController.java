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

@RestController
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(
            EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("evaluations/years")
    public ResponseEntity<List<YearResponse>> getYears() {
        List<YearResponse> result = evaluationService.getYears();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/evaluations")
    public ResponseEntity<List<CourseOfYearResponse>> getAllEvaluations() {
        List<CourseOfYearResponse> result = evaluationService.getEvaluations();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/evaluation")
    public ResponseEntity<List<EvaluationResponse>> doAnnualEvaluation(@RequestParam int year, @RequestParam boolean finalize) throws IOException, CoyFailedException {
        List<EvaluationResponse> result = evaluationService.runCoy(year, finalize);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping(value = "/evaluation/export", produces = "text/csv")
    public void doAnnualEvaluationExportCsv(@RequestParam int year, @RequestParam boolean finalize, HttpServletResponse response) throws IOException, CoyFailedException {
        evaluationService.runCoy(year, finalize, response);
    }
}
