package org.spring.cp.springprojectmay2024.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.cp.springprojectmay2024.dto.UserDTO;
import org.spring.cp.springprojectmay2024.dto.UserLoginRequestDTO;
import org.spring.cp.springprojectmay2024.dto.UserLoginResponseDTO;
import org.spring.cp.springprojectmay2024.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(authService.register(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO) {
        return ResponseEntity.ok(authService.login(userLoginRequestDTO));
    }
}
