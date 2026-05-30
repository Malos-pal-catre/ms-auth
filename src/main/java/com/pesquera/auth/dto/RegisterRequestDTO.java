package com.pesquera.auth.dto;

import com.pesquera.auth.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(

    @NotBlank(message = "El username es obligatorio")
    String username,

    @NotBlank(message = "La contraseña es obligatoria")
    String password,

    @NotNull(message = "El rol es obligatorio")
    Usuario.Rol rol
) {}