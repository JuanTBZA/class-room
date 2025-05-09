package com.juantirado.virtual_classroom.model.entity.academic;

import com.juantirado.virtual_classroom.model.entity.auth.Teacher;
import com.juantirado.virtual_classroom.model.enums.Day;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "scheduled_course")
public class ScheduledCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @NotNull
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private Day day;

    @NotNull
    @Column(name = "start_time")
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time")
    private LocalTime endTime;

}
