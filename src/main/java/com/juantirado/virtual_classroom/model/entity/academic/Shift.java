package com.juantirado.virtual_classroom.model.entity.academic;

import com.juantirado.virtual_classroom.model.entity.enrollment.PriceHistory;
import com.juantirado.virtual_classroom.model.enums.ShiftModality;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shift")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private ShiftModality modality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    private Semester semester;

}
