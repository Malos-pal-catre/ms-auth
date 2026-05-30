package com.pesquera.auth.controller;

import com.pesquera.auth.dto.*;
import com.pesquera.auth.model.Usuario;
import com.pesquera.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registrar(@RequestBody @Valid RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(dto));
    }

    @GetMapping("/usuarios/rol")
    public ResponseEntity<List<AuthResponseDTO>> listarPorRol(@RequestParam Usuario.Rol rol) {
        return ResponseEntity.ok(authService.listarPorRol(rol));
    }
}