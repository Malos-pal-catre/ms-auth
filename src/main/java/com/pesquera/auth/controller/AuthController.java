package com.pesquera.auth.controller;

import com.pesquera.auth.dto.*;
import com.pesquera.auth.model.Usuario;
import com.pesquera.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Autenticación y registro de usuarios del sistema (JWT)")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Iniciar sesión", description = "Valida las credenciales y retorna un token JWT para autenticar al usuario en los demás microservicios.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = @Content(
            schema = @Schema(implementation = LoginRequestDTO.class),
            examples = @ExampleObject(
                name = "Login de ejemplo",
                value = """
                {
                  "username": "pgonzalez",
                  "password": "claveSegura123"
                }
                """
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso, retorna token JWT"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea una cuenta nueva en el sistema asociada a un rol (ej: PESCADOR, COMPRADOR, ADMIN).")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = @Content(
            schema = @Schema(implementation = RegisterRequestDTO.class),
            examples = @ExampleObject(
                name = "Registro de ejemplo",
                value = """
                {
                  "username": "pgonzalez",
                  "password": "claveSegura123",
                  "rol": "PESCADOR"
                }
                """
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o username ya existente", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registrar(@RequestBody @Valid RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(dto));
    }

    @Operation(summary = "Listar usuarios por rol", description = "Retorna todos los usuarios registrados que tengan un rol específico.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/usuarios/rol")
    public ResponseEntity<List<AuthResponseDTO>> listarPorRol(
            @Parameter(description = "Rol a filtrar", example = "PESCADOR") @RequestParam Usuario.Rol rol) {
        return ResponseEntity.ok(authService.listarPorRol(rol));
    }
}