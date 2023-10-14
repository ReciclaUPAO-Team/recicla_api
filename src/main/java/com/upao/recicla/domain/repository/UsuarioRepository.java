package com.upao.recicla.domain.repository;

import com.upao.recicla.domain.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
