package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.common.exception.ResourceNotFoundException;
import com.juantirado.virtual_classroom.dto.PaginatedResponseDto;
import com.juantirado.virtual_classroom.dto.academic.CourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.CourseResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Course;
import com.juantirado.virtual_classroom.mapper.academic.CourseMapper;
import com.juantirado.virtual_classroom.repository.academic.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course courseEntity;
    private CourseRequestDto courseRequestDto;
    private CourseResponseDto courseResponseDto;

    @BeforeEach
    void setUp() {
        courseEntity = Course.builder()
                .id(1L)
                .name("Matemáticas")
                .description("Curso de matemáticas básicas")
                .build();

        courseRequestDto = new CourseRequestDto("Matemáticas", "Curso de matemáticas básicas");
        courseResponseDto = new CourseResponseDto(1L, "Matemáticas", "Curso de matemáticas básicas");
    }

    @Test
    void getAllReturnsAllMappedCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(courseEntity));
        when(courseMapper.toResponseDto(courseEntity)).thenReturn(courseResponseDto);

        List<CourseResponseDto> result = courseService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseResponseDto, result.get(0));
        verify(courseRepository).findAll();
        verify(courseMapper).toResponseDto(courseEntity);
    }

    @Test
    void getByIdReturnsMappedCourseWhenFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));
        when(courseMapper.toResponseDto(courseEntity)).thenReturn(courseResponseDto);

        CourseResponseDto result = courseService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Matemáticas", result.name());
        verify(courseRepository).findById(1L);
        verify(courseMapper).toResponseDto(courseEntity);
    }

    @Test
    void getByIdThrowsResourceNotFoundWhenMissing() {
        when(courseRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> courseService.getById(2L));
        assertTrue(ex.getMessage().contains("El curso con ID 2 no existe"));
        verify(courseRepository).findById(2L);
        verifyNoInteractions(courseMapper);
    }

    @Test
    void createSavesEntityAndReturnsMappedResponse() {
        when(courseMapper.toEntity(courseRequestDto)).thenReturn(courseEntity);
        when(courseRepository.save(courseEntity)).thenReturn(courseEntity);
        when(courseMapper.toResponseDto(courseEntity)).thenReturn(courseResponseDto);

        CourseResponseDto result = courseService.create(courseRequestDto);

        assertNotNull(result);
        assertEquals(courseResponseDto, result);
        verify(courseMapper).toEntity(courseRequestDto);
        verify(courseRepository).save(courseEntity);
        verify(courseMapper).toResponseDto(courseEntity);
    }

    @Test
    void updateFindsEntityUpdatesSavesAndReturnsMappedResponse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));
        // updateEntity is void; we just verify it is called
        when(courseRepository.save(any(Course.class))).thenReturn(courseEntity);
        when(courseMapper.toResponseDto(courseEntity)).thenReturn(courseResponseDto);

        CourseResponseDto result = courseService.update(1L, courseRequestDto);

        assertNotNull(result);
        assertEquals(courseResponseDto, result);
        verify(courseRepository).findById(1L);
        verify(courseMapper).updateEntity(courseRequestDto, courseEntity);
        verify(courseRepository).save(courseEntity);
        verify(courseMapper).toResponseDto(courseEntity);
    }

    @Test
    void updateThrowsResourceNotFoundWhenEntityMissing() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> courseService.update(99L, courseRequestDto));
        assertTrue(ex.getMessage().contains("El curso con ID 99 no existe"));
        verify(courseRepository).findById(99L);
        verifyNoInteractions(courseMapper);
    }

    @Test
    void getCoursesByPageReturnsPaginatedResponseAscending() {
        List<Course> content = List.of(courseEntity);
        when(courseRepository.findCoursesByName(eq("mat"), any(PageRequest.class)))
                .thenAnswer(invocation -> new PageImpl<>(content, invocation.getArgument(1), 1));
        when(courseMapper.toResponseDto(courseEntity)).thenReturn(courseResponseDto);

        PaginatedResponseDto<CourseResponseDto> page = courseService.getCoursesByPage("mat", 0, 10, "name", "asc");

        assertNotNull(page);
        assertEquals(1, page.totalElements());
        assertEquals(1, page.content().size());
        assertEquals(courseResponseDto, page.content().get(0));
    }

    @Test
    void getCoursesByPageReturnsEmptyWhenNoResults() {
        when(courseRepository.findCoursesByName(eq("none"), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10, Sort.by("name")), 0));

        PaginatedResponseDto<CourseResponseDto> page = courseService.getCoursesByPage("none", 0, 10, "name", "asc");

        assertNotNull(page);
        assertEquals(0, page.totalElements());
        assertTrue(page.content().isEmpty());
    }

    @Test
    void deleteRemovesEntityAndReturnsMappedResponseWhenFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));
        when(courseMapper.toResponseDto(courseEntity)).thenReturn(courseResponseDto);

        CourseResponseDto result = courseService.delete(1L);

        assertNotNull(result);
        assertEquals(courseResponseDto, result);
        verify(courseRepository).findById(1L);
        verify(courseRepository).deleteById(1L);
        verify(courseMapper).toResponseDto(courseEntity);
    }

    @Test
    void deleteThrowsResourceNotFoundWhenMissing() {
        when(courseRepository.findById(5L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> courseService.delete(5L));
        assertTrue(ex.getMessage().contains("El curso con ID 5 no existe"));
        verify(courseRepository).findById(5L);
        verify(courseRepository, never()).deleteById(anyLong());
        verifyNoInteractions(courseMapper);
    }
}
