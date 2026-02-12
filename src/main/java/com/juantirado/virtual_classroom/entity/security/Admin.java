package com.juantirado.virtual_classroom.entity.security;

import com.juantirado.virtual_classroom.entity.auth.User;
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
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @JoinColumn(name = "created_by_admin_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin createdByAdmin;

    @Column(name = "is_master", nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean isMaster = false;

}
