package de.dlsa.api.controllers;

import de.dlsa.api.dtos.BookingDto;
import de.dlsa.api.responses.BookingResponse;
import de.dlsa.api.services.JournalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/journal")
@CrossOrigin(origins = "http://localhost:4200")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> getBookings() {
        return ResponseEntity.ok(journalService.getBookings());
    }

    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingDto booking) {
        BookingResponse created = journalService.createBooking(booking);
        return ResponseEntity.ok(created);
    }

    /*
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable long id) {
        BookingResponse canceled = journalService.cancelBooking(id);
        return ResponseEntity.ok(canceled);
    }

     */

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable long id) {
        journalService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export-csv")
    public ResponseEntity<byte[]> exportCsv() throws IOException {
        return journalService.generateAndExportCsv();
    }
}
