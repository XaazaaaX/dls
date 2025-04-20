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
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class JournalService {

    private final GroupRepository groupRepository;
    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final ActionRepository actionRepository;
    private final YearRepository yearRepository;
    private final ModelMapper modelMapper;

    public JournalService(
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

        int year = booking.getDoneDate().atZone(ZoneId.systemDefault()).getYear();
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

        //return modelMapper.map(canceledBooking, BookingResponse.class);
    }







    public ResponseEntity<byte[]> generateAndExportCsv() throws IOException {


        handleMemberData();
















        String filename = "data.csv";
        List<String[]> dataLines = Arrays.asList(
                new String[]{"ID", "Name", "Email"},
                new String[]{"1", "Alice", "alice@example.com"},
                new String[]{"2", "Bob", "bob@example.com"}
        );
        // 1. CSV-Datei lokal speichern
        saveCsvToDisk(dataLines, filename);

        // 2. CSV als ByteArray zum Download zurückgeben
        byte[] csvBytes = convertToByteArray(dataLines);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvBytes);
    }

    private void saveCsvToDisk(List<String[]> dataLines, String filename) throws IOException {
        Path resourcePath = Paths.get("src/main/resources/exports", filename);
        Files.createDirectories(resourcePath.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(resourcePath)) {
            for (String[] line : dataLines) {
                writer.write(String.join(";", line));
                writer.newLine();
            }
        }
    }

    private byte[] convertToByteArray(List<String[]> dataLines) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {

            for (String[] line : dataLines) {
                writer.write(String.join(";", line));
                writer.newLine();
            }
            writer.flush(); // wichtig!
            return out.toByteArray();
        }
    }

    private void handleMemberData(){






    }
}
