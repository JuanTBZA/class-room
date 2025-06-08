package com.juantirado.virtual_classroom.controller.auth;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable long id) {
        UserResponseDto user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable long id) {
        UserResponseDto deletedUser = userService.delete(id);
        return ResponseEntity.ok(deletedUser);
    }

}
