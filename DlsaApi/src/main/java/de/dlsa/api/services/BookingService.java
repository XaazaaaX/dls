package de.dlsa.api.services;

import de.dlsa.api.dtos.BookingDto;
import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.*;
import de.dlsa.api.responses.BookingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service zur Verwaltung von Buchungen (DLS-Einträgen).
 * Bietet Methoden zum Abrufen, Erstellen und Stornieren von Buchungen.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Service
public class BookingService {

    private final GroupRepository groupRepository;
    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final ActionRepository actionRepository;
    private final YearRepository yearRepository;
    private final ModelMapper modelMapper;

    /**
     * Konstruktor zur Bereitstellung aller benötigten Repository- und Mapper-Abhängigkeiten.
     *
     * @param actionRepository   Repository für Aktionen
     * @param groupRepository    Repository für Gruppen
     * @param bookingRepository  Repository für Buchungen
     * @param memberRepository   Repository für Mitglieder
     * @param yearRepository     Repository für Jahre
     * @param modelMapper        Mapper zur Konvertierung von DTOs und Entitäten
     */
    public BookingService(
            ActionRepository actionRepository,
            GroupRepository groupRepository,
            BookingRepository bookingRepository,
            MemberRepository memberRepository,
            YearRepository yearRepository,
            ModelMapper modelMapper) {
        this.actionRepository = actionRepository;
        this.groupRepository = groupRepository;
        this.bookingRepository = bookingRepository;
        this.memberRepository = memberRepository;
        this.yearRepository = yearRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Gibt alle Buchungen sortiert nach dem Leistungsdatum (absteigend) zurück.
     *
     * @return Liste aller Buchungen als {@link BookingResponse}
     */
    public List<BookingResponse> getBookings() {
        return bookingRepository.findAll().stream()
                .sorted(Comparator.comparing(Booking::getDoneDate).reversed())
                .map(booking -> modelMapper.map(booking, BookingResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Erstellt eine neue Buchung basierend auf einem übergebenen {@link BookingDto}.
     * Wenn das Jahr des Leistungsdatums nicht existiert, wird es automatisch angelegt.
     *
     * @param booking Buchungsdaten
     * @return Die erstellte Buchung als {@link BookingResponse}
     * @throws RuntimeException Wenn Mitglied oder Aktion nicht gefunden werden
     */
    public BookingResponse createBooking(BookingDto booking) {
        Booking mappedBooking = modelMapper.map(booking, Booking.class);

        if (booking.getMemberId() != null) {
            Member member = memberRepository.findById(booking.getMemberId())
                    .orElseThrow(() -> new RuntimeException("Mitglied nicht gefunden"));
            mappedBooking.setMember(member);
        }

        if (booking.getActionId() != null) {
            Action action = actionRepository.findById(booking.getActionId())
                    .orElseThrow(() -> new RuntimeException("Aktion nicht gefunden"));
            mappedBooking.setAction(action);
        }

        int year = booking.getDoneDate().getYear();
        yearRepository.findByYear(year)
                .orElseGet(() -> yearRepository.save(new Year().setYear(year)));

        Booking addedBooking = bookingRepository.save(mappedBooking);
        return modelMapper.map(addedBooking, BookingResponse.class);
    }

    /**
     * Storniert eine bestehende Buchung anhand der ID. Dabei wird:
     * - die ursprüngliche Buchung als storniert markiert
     * - eine neue Gegenbuchung mit negativem DLS-Wert erstellt
     *
     * @param id ID der zu stornierenden Buchung
     * @throws RuntimeException Wenn die Buchung nicht gefunden wird
     */
    public void cancelBooking(long id) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Buchung wurde nicht gefunden!"));

        existing.setCanceled(true);
        bookingRepository.save(existing);

        Booking newBooking = new Booking()
                .setAction(existing.getAction())
                .setComment("Stornierung von: " + existing.getComment())
                .setDoneDate(existing.getDoneDate())
                .setMember(existing.getMember())
                .setCountDls(existing.getCountDls() * -1);

        bookingRepository.save(newBooking);
    }
}
