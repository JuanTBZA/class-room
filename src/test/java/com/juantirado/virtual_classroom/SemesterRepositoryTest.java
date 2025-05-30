package com.juantirado.virtual_classroom;

import com.juantirado.virtual_classroom.entity.academic.Semester;
import com.juantirado.virtual_classroom.repository.academic.SemesterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "PORT=8080",
        "DB_USERNAME=root",
        "DB_PASSWORD="
})
public class SemesterRepositoryTest {

    @Autowired
    private SemesterRepository semesterRepository;

    @Test
    void shouldSaveSemester(){
        Semester semester = new Semester();
        semester.setName("2025-3");
        semester.setStartDate(LocalDate.of(2025, 4, 24));
        semester.setEndDate(LocalDate.of(2025, 11, 20));
        var saved = semesterRepository.save(semester);
        assertNotNull(saved.getId());
        assertEquals("2025-3",saved.getName());


    }

    @Test
    void whenGetAllSemester_ThenGetAllSemester(){
        var list = semesterRepository.findAll();
        assertEquals(2,list.size());
    }

    //Semesters has been finished
    @Test
    void whenGetAllSemesterFinished_ThenGetAllSemesterFinished(){
        var list = semesterRepository.findByEndDateBefore(LocalDate.now());
        assertEquals(1,list.size());
    }

    //Semesters are in progress
    @Test
    void whenGetAllSemesterInProgress_ThenGetAllSemesterInProgress(){
        var list = semesterRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate.now(),LocalDate.now());
        assertEquals(1,list.size());
    }

    //Semester are in the future
    @Test
    void WhenGetAllSemesterAreInTheFuture_ThenGetAllSemesterAreInTheFuture(){
        var list = semesterRepository.findByStartDateGreaterThan(LocalDate.now());
        assertEquals(0,list.size());

    }



}
