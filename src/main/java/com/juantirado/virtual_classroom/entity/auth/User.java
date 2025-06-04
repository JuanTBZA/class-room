package com.juantirado.virtual_classroom.entity.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
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
    private Boolean enabled;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @PrePersist
    void setPersist(){
        enabled = true;
    }

}
