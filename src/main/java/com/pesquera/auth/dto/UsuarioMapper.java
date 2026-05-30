package com.pesquera.auth.dto;

import com.pesquera.auth.model.Usuario;

public class UsuarioMapper {

    public static AuthResponseDTO toDTO(Usuario u, String token) {
        return new AuthResponseDTO(
            u.getId(),
            u.getUsername(),
            u.getRol().name(),
            token
        );
    }

    public static Usuario toEntity(RegisterRequestDTO dto, String passwordEncriptado) {
        return Usuario.builder()
            .username(dto.username())
            .password(passwordEncriptado)
            .rol(dto.rol())
            .activo(true)
            .build();
    }
}