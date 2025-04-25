package com.juantirado.virtual_classroom.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @Column(name = "contract_date_start")
    private LocalDateTime contractDateStart;

    @Column(name = "contract_date_end")
    private LocalDateTime contractDateEnd;

    private String specialization;
}
