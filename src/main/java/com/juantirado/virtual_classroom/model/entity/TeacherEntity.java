package com.juantirado.virtual_classroom.model.entity;

import jakarta.persistence.*;
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
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @Column(name = "contract_date_star")
    private LocalDateTime contractDateStar;

    @Column(name = "contract_date_end")
    private LocalDateTime contractDateEnd;

    private String specialization;
}
