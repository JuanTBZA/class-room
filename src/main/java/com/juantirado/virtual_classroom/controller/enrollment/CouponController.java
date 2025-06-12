package com.juantirado.virtual_classroom.controller.enrollment;

import com.juantirado.virtual_classroom.dto.enrollment.CouponPatchRequestDto;
import com.juantirado.virtual_classroom.dto.enrollment.CouponRequestDto;
import com.juantirado.virtual_classroom.dto.enrollment.CouponResponseDto;
import com.juantirado.virtual_classroom.service.enrollment.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponResponseDto> create(@RequestBody CouponRequestDto dto) {
        return ResponseEntity.ok(couponService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CouponResponseDto>> getAll() {
        List<CouponResponseDto> coupons = couponService.getAll();
        return coupons.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(coupons);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CouponResponseDto> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(couponService.getByCode(code));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CouponResponseDto> patch(@PathVariable Long id, @RequestBody CouponPatchRequestDto dto) {
        return ResponseEntity.ok(couponService.patch(id, dto));
    }

}
