package com.juantirado.virtual_classroom.model.entity;

import com.juantirado.virtual_classroom.model.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserEntity {

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

    private Boolean active;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Column(name = "role_id")
    @OneToMany(fetch = FetchType.LAZY)
    @NotNull
    private RolEntity rol;

    @PrePersist
    void setPersit(){
        active = true;
    }

}
