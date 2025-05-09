package com.juantirado.virtual_classroom.repository.enrollment;

import com.juantirado.virtual_classroom.model.entity.enrollment.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
}
