package com.juantirado.virtual_classroom.entity.academic;

import com.juantirado.virtual_classroom.entity.auth.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @Column(name = "contract_date_start")
    private LocalDate contractDateStart;

    @Column(name = "contract_date_end")
    private LocalDate contractDateEnd;

    private String specialization;
}
