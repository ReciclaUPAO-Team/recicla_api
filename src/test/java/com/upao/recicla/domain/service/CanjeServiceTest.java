package com.upao.recicla.domain.service;

import com.upao.recicla.domain.entity.Canje;
import com.upao.recicla.domain.entity.Recompensa;
import com.upao.recicla.domain.entity.Usuario;
import com.upao.recicla.domain.repository.CanjeRepository;
import com.upao.recicla.domain.repository.RecompensaRepository;
import com.upao.recicla.domain.repository.UsuarioRepository;
import com.upao.recicla.domain.service.CanjeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CanjeServiceTest {

    @Test
    void canjearPuntos() throws Exception {
        // Creamos mocks de los repositorios
        CanjeRepository mockCanjeRepository = Mockito.mock(CanjeRepository.class);
        UsuarioRepository mockUsuarioRepository = Mockito.mock(UsuarioRepository.class);
        RecompensaRepository mockRecompensaRepository = Mockito.mock(RecompensaRepository.class);

        // Creamos una instancia del servicio de canje
        CanjeService canjeService = new CanjeService(mockCanjeRepository, mockUsuarioRepository, mockRecompensaRepository);

        // Creamos un usuario
        Usuario usuario = new Usuario();
        usuario.setPuntos(100.0);

        // Creamos una recompensa
        Recompensa recompensa = new Recompensa();
        recompensa.setValor(50.0);

        // Simular el comportamiento de los repositorios
        Mockito.when(mockUsuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        Mockito.when(mockRecompensaRepository.findById(recompensa.getId())).thenReturn(Optional.of(recompensa));

        // Canjear puntos
        canjeService.canjearPuntos(usuario.getId(), recompensa.getId());

        // Assert que el usuario tenga menos puntos
        assertEquals(50, usuario.getPuntos());

        // Assert que se haya creado un nuevo canje
        Mockito.verify(mockCanjeRepository).save(Mockito.any(Canje.class));

    }
}