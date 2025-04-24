package com.juantirado.virtual_classroom.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private UserEntity user;

    @Column(name = "university_headquarters")
    private String universityHeadquarters;

    @Column(name = "intended_major")
    private String intendedMajor;
}
