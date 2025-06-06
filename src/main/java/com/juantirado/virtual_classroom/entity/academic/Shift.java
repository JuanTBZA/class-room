    package com.juantirado.virtual_classroom.entity.academic;

    import com.juantirado.virtual_classroom.entity.enums.ShiftModality;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotEmpty;
    import jakarta.validation.constraints.NotNull;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.math.BigDecimal;

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

        @NotNull
        @Enumerated(EnumType.STRING)
        private ShiftModality modality;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "semester_id"    )
        private Semester semester;

        private BigDecimal price;

    }
