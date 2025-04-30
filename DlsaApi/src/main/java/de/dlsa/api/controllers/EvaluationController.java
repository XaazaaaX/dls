package de.dlsa.api.controllers;

import de.dlsa.api.responses.CourseOfYearResponse;
import de.dlsa.api.responses.EvaluationResponse;
import de.dlsa.api.responses.YearResponse;
import de.dlsa.api.services.EvaluationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(
            EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("evaluations/years")
    public ResponseEntity<List<YearResponse>> getYears() {
        List<YearResponse> result = evaluationService.getYears();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/evaluations")
    public ResponseEntity<List<CourseOfYearResponse>> getAllEvaluations() {
        List<CourseOfYearResponse> result = evaluationService.getEvaluations();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/evaluation")
    public ResponseEntity<List<EvaluationResponse>> doAnnualEvaluation(@RequestParam int year, @RequestParam boolean finalize) throws IOException {
        List<EvaluationResponse> result = evaluationService.calculateForYear(year, finalize);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/evaluation/export", produces = "text/csv")
    public void doAnnualEvaluationExportCsv(@RequestParam int year, @RequestParam boolean finalize, HttpServletResponse response) throws IOException {
        evaluationService.calculateForYear(year, finalize, response);
    }
}
