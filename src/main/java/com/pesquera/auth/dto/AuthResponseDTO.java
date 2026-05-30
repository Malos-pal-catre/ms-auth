package com.pesquera.auth.dto;

public record AuthResponseDTO(
    Long id,
    String username,
    String rol,
    String token
) {}