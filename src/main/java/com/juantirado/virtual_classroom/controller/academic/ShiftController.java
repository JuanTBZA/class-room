package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.dto.academic.ShiftRequestDto;
import com.juantirado.virtual_classroom.dto.academic.ShiftResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Shift;
import com.juantirado.virtual_classroom.service.academic.ShiftService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shifts")
public class ShiftController {

    private final ShiftService shiftService;

    @GetMapping
    public ResponseEntity<List<ShiftResponseDto>> getAllShifts() {
        List<ShiftResponseDto> shift = shiftService.getAll();
        if (shift.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shift);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftResponseDto> getShiftById(@PathVariable("id") Long id) {
        ShiftResponseDto shift = shiftService.findById(id);
        if (shift == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shift);
    }

    @PostMapping
    public ResponseEntity<ShiftResponseDto> createShift(@Valid @RequestBody ShiftRequestDto shiftRequestDto) {
        ShiftResponseDto shift = shiftService.create(shiftRequestDto);
        return new ResponseEntity<>(shift, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftResponseDto> updateShift(@PathVariable Long id, @Valid @RequestBody ShiftRequestDto shiftRequestDto) {
        ShiftResponseDto shiftResponseDto= shiftService.update(shiftRequestDto, id);
        if (shiftResponseDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shiftResponseDto);
    }



}
