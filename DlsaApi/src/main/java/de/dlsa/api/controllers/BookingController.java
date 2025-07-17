package de.dlsa.api.controllers;

import de.dlsa.api.dtos.BookingDto;
import de.dlsa.api.responses.BookingResponse;
import de.dlsa.api.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Verarbeitung von Anfragen zur Buchung, Stornierung und Abfrage von Buchungen
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RestController
public class BookingController {

    private final BookingService bookingService;

    /**
     * Konstruktor
     *
     * @param bookingService Service zur Weiterverarbeitung der Buchungsanfragen
     */
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Endpunkt zur Abfrage aller Buchungen
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @return Liste aller Buchungen als BookingResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> getBookings() {
        return ResponseEntity.ok(bookingService.getBookings());
    }

    /**
     * Endpunkt zum Erstellen einer neuen Buchung
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param booking Buchungsdaten als BookingDto (validiert)
     * @return Die erstellte Buchung als BookingResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingDto booking) {
        BookingResponse created = bookingService.createBooking(booking);
        return ResponseEntity.ok(created);
    }

    /**
     * Endpunkt zum Stornieren einer bestehenden Buchung anhand der ID
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param id ID der zu stornierenden Buchung
     * @return HTTP 204 No Content bei erfolgreicher Stornierung
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}
