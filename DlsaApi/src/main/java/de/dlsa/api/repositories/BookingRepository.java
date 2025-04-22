package de.dlsa.api.repositories;

import de.dlsa.api.entities.Booking;
import de.dlsa.api.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT COALESCE(SUM(b.countDls), 0) FROM Booking b WHERE b.member = :member AND b.doneDate BETWEEN :fromDate AND :toDate")
    double getSumDlsByMemberAndDateRange(@Param("member") Member member,
                                         @Param("fromDate") LocalDateTime fromDate,
                                         @Param("toDate") LocalDateTime toDate);
}

