package com.juantirado.virtual_classroom.repository.academic;

import com.juantirado.virtual_classroom.entity.academic.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SemesterRepository  extends JpaRepository<Semester, Long> {
    List<Semester> findByEndDateBefore(LocalDate currentDate);
    List<Semester> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate start, LocalDate end);
    List<Semester> findByStartDateGreaterThan(LocalDate currentDate);

}