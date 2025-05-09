package com.juantirado.virtual_classroom.repository.academic;

import com.juantirado.virtual_classroom.model.entity.academic.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
