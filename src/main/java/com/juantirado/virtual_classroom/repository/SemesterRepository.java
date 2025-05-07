package com.juantirado.virtual_classroom.repository;

import com.juantirado.virtual_classroom.model.entity.academic.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SemesterRepository  extends JpaRepository<Semester, Long> {
    List<Semester> findByEndDateBefore(LocalDate currentDate);
    List<Semester> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate start, LocalDate end);
    List<Semester> findByStartDateGreaterThan(LocalDate currentDate);

}