package com.juantirado.virtual_classroom.model.entity;

import com.juantirado.virtual_classroom.model.enums.UserStatus;
import jakarta.persistence.*;
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

    private String name;

    private String dni;

    private String password;

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
    private RolEntity rol;

    @PrePersist
    void setPersit(){
        active = true;
    }

}
