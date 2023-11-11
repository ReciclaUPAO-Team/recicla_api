package com.upao.recicla.domain.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.upao.recicla.domain.entity.Recompensa;
import com.upao.recicla.domain.repository.RecompensaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;

class RecompensaServiceTest {

    @Test
    void addRecompensa() {
        // Creamos un mock de la clase RecompensaRepository
        RecompensaRepository recompensaRepositoryMock = Mockito.mock(RecompensaRepository.class);

        // Creamos una instancia del servicio de recompensas
        RecompensaService recompensaService = new RecompensaService(recompensaRepositoryMock);

        // Creamos una recompensa
        Recompensa recompensa = new Recompensa();
        recompensa.setTitulo("Título de la recompensa");
        recompensa.setDescripcion("Descripción de la recompensa");
        recompensa.setCategoria("Categoría de la recompensa");
        recompensa.setValor(100.0);

        // Simulamos el comportamiento del repositorio
        Mockito.when(recompensaRepositoryMock.save(recompensa)).thenReturn(recompensa);

        // Agregamos la recompensa
        Recompensa recompensaAgregada = recompensaService.addRecompensa(recompensa);

        // Assert que la recompensa se haya agregado correctamente
        assertEquals(recompensa, recompensaAgregada);

        // Assert que el repositorio haya sido llamado para guardar la recompensa
        Mockito.verify(recompensaRepositoryMock).save(recompensa);
    }
}