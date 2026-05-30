package com.pesquera.auth.repository;

import com.pesquera.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Query Methods
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findByRol(Usuario.Rol rol);
    List<Usuario> findByActivoTrue();

    // Custom Query
    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol AND u.activo = true")
    List<Usuario> usuariosActivosPorRol(@Param("rol") Usuario.Rol rol);

    @Query(value = "SELECT COUNT(*) FROM usuarios WHERE rol = :rol", nativeQuery = true)
    Long contarPorRol(@Param("rol") String rol);
}