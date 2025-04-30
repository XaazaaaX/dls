package de.dlsa.api.controllers;

import de.dlsa.api.dtos.BookingDto;
import de.dlsa.api.responses.BookingResponse;
import de.dlsa.api.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(
            BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> getBookings() {
        return ResponseEntity.ok(bookingService.getBookings());
    }

    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingDto booking) {
        BookingResponse created = bookingService.createBooking(booking);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}
