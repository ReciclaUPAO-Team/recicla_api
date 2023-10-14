package com.upao.recicla.domain.service;

import com.upao.recicla.domain.entity.usuario.Usuario;
import com.upao.recicla.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }
    public void addUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
    public void updateUsuario(Usuario usuario, Long id) {
        Usuario usuarioExists = usuarioRepository.findById(id)
                .orElse(new Usuario());

        usuarioExists.setNombre(usuario.getNombre());
        usuarioRepository.save(usuarioExists);
    }
    public void deleteUsuarioById(Long id) {
        usuarioRepository.deleteById(id);
    }
}
