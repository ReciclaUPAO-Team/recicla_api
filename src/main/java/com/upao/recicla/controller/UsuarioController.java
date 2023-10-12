package com.upao.recicla.controller;

import com.upao.recicla.domain.entity.actividad.Actividad;
import com.upao.recicla.domain.entity.usuario.Usuario;
import com.upao.recicla.domain.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }
    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id)
                .orElse(new Usuario());
    }
    @PostMapping
    public void addUsuario(@RequestBody Usuario usuario) {
        usuarioService.addUsuario(usuario);
    }
    @PutMapping("/{id}")
    public void updateUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
        usuarioService.updateUsuario(usuario, id);
    }
    @DeleteMapping("/{id}")
    public void deleteUsuarioById(@PathVariable Long id) {
        usuarioService.deleteUsuarioById(id);
    }

}
