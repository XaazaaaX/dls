package de.dlsa.api.services;

import de.dlsa.api.dtos.BookingDto;
import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.*;
import de.dlsa.api.responses.BookingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class BookingService {

    private final GroupRepository groupRepository;
    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final ActionRepository actionRepository;
    private final YearRepository yearRepository;
    private final ModelMapper modelMapper;

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

    public List<BookingResponse> getBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .sorted(Comparator.comparing(Booking::getDoneDate).reversed())
                .map(booking -> modelMapper.map(booking, BookingResponse.class))
                .collect(Collectors.toList());
    }

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
