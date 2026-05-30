package com.pesquera.auth.service;

import com.pesquera.auth.dto.*;
import com.pesquera.auth.exception.RecursoNoEncontradoException;
import com.pesquera.auth.model.Usuario;
import com.pesquera.auth.repository.UsuarioRepository;
import com.pesquera.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO login(LoginRequestDTO dto) {
        Usuario u = usuarioRepository.findByUsername(dto.username())
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + dto.username()));

        if (!passwordEncoder.matches(dto.password(), u.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        if (!u.getActivo()) {
            throw new IllegalArgumentException("Usuario inactivo");
        }

        String token = jwtUtil.generarToken(u.getUsername(), u.getRol().name());
        return UsuarioMapper.toDTO(u, token);
    }

    public AuthResponseDTO registrar(RegisterRequestDTO dto) {
        if (usuarioRepository.findByUsername(dto.username()).isPresent()) {
            throw new IllegalArgumentException("Ya existe el usuario: " + dto.username());
        }
        String passwordEncriptado = passwordEncoder.encode(dto.password());
        Usuario nuevo = UsuarioMapper.toEntity(dto, passwordEncriptado);
        return UsuarioMapper.toDTO(usuarioRepository.save(nuevo), null);
    }

    public List<AuthResponseDTO> listarPorRol(Usuario.Rol rol) {
        return usuarioRepository.usuariosActivosPorRol(rol)
            .stream()
            .map(u -> UsuarioMapper.toDTO(u, null))
            .toList();
    }
}