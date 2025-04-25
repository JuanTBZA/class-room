package com.juantirado.virtual_classroom.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(unique = true)
    @NotBlank
    private String dni;

    @NotBlank
    private String password;

    @Column(unique = true)
    @NotBlank
    private String email;

    @Column(columnDefinition = "BIT(1) default 1")
    private Boolean active;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @JoinColumn(name = "role_id")
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Role role;

    @PrePersist
    void setPersit(){
        active = true;
    }

}
