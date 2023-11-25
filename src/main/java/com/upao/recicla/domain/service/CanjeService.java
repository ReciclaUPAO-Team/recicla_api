package com.upao.recicla.domain.service;

import com.upao.recicla.domain.entity.Canje;
import com.upao.recicla.domain.entity.Recompensa;
import com.upao.recicla.domain.entity.Usuario;
import com.upao.recicla.domain.repository.CanjeRepository;
import com.upao.recicla.domain.repository.RecompensaRepository;
import com.upao.recicla.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CanjeService {

    @Autowired
    private final CanjeRepository canjeRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final RecompensaRepository recompensaRepository;

    public CanjeService(CanjeRepository canjeRepository, UsuarioRepository usuarioRepository,
                        RecompensaRepository recompensaRepository) {
        this.canjeRepository = canjeRepository;
        this.usuarioRepository = usuarioRepository;
        this.recompensaRepository = recompensaRepository;
    }

    @Transactional
    public void canjearPuntos(Long idUsuario, Long idRecompensa) throws Exception {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow();

        Recompensa recompensa = recompensaRepository.findById(idRecompensa).orElseThrow();

        if (usuario.getPuntos() < recompensa.getValor()) {
            throw new Exception("No tienes suficientes puntos para canjear esta recompensa");
        }

        usuario.setPuntos(usuario.getPuntos() - recompensa.getValor());

        Canje canje = new Canje();
        canje.setFecha(LocalDate.now());
        canje.setPuntosCanjear(recompensa.getValor());
        canje.setUsuario(usuario);
        canje.setRecompensa(recompensa);

        usuarioRepository.save(usuario);
        canjeRepository.save(canje);
    }

}
