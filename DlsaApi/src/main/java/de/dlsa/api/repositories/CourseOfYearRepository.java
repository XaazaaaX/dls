package de.dlsa.api.repositories;

import de.dlsa.api.entities.CourseOfYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface CourseOfYearRepository extends JpaRepository<CourseOfYear, Long> {
    @Query("SELECT MAX(j.dueDate) FROM CourseOfYear j")
    LocalDateTime findLastDoneDate();
}
