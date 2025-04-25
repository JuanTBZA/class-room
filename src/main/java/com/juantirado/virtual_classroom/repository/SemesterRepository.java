package com.juantirado.virtual_classroom.repository;

import com.juantirado.virtual_classroom.model.entity.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SemesterRepository  extends JpaRepository<SemesterEntity, Long> {
    List<SemesterEntity> findByEndDateBefore(LocalDate currentDate);
    List<SemesterEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate start, LocalDate end);
    List<SemesterEntity> findByStartDateGreaterThan(LocalDate currentDate);

}