package de.dlsa.api.repositories;

import de.dlsa.api.entities.CourseOfYear;
import de.dlsa.api.entities.GroupChanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface CourseOfyearRepository extends JpaRepository<CourseOfYear, Long> {
    @Query("SELECT MAX(j.dueDate) FROM CourseOfYear j")
    LocalDateTime findLastDoneDate();
}
