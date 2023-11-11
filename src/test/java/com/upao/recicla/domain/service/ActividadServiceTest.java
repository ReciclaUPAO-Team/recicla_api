package com.upao.recicla.domain.service;

import com.upao.recicla.domain.entity.Actividad;
import com.upao.recicla.domain.entity.Residuo;
import com.upao.recicla.domain.entity.Usuario;
import com.upao.recicla.domain.repository.ActividadRepository;
import com.upao.recicla.domain.repository.ResiduoRepository;
import com.upao.recicla.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class ActividadServiceTest {

    @Test
    void getAllActividades() {
        // Creamos mocks de los repositorios necesarios
        ActividadRepository actividadRepositoryMock = Mockito.mock(ActividadRepository.class);
        ResiduoRepository residuoRepositoryMock = Mockito.mock(ResiduoRepository.class);
        UsuarioRepository usuarioRepositoryMock = Mockito.mock(UsuarioRepository.class);

        // Creamos una instancia del servicio de actividades con los mocks
        ActividadService actividadService = new ActividadService(
                actividadRepositoryMock,
                residuoRepositoryMock,
                usuarioRepositoryMock
        );

        // Simulamos la respuesta del repositorio de actividades
        Page<Actividad> actividades = Page.empty();
        Mockito.when(actividadRepositoryMock.findAll(any(Pageable.class))).thenReturn(actividades);

        // Obtenemos todas las actividades
        Page<Actividad> actividadesObtenidas = actividadService.getAllActividades(PageRequest.of(0, 10));

        // Assert que la página de actividades obtenida sea la misma que la simulada
        assertEquals(actividades, actividadesObtenidas);
    }

    @Test
    void getReferenceById() {
        // Creamos mocks de los repositorios necesarios
        ActividadRepository actividadRepositoryMock = Mockito.mock(ActividadRepository.class);
        ResiduoRepository residuoRepositoryMock = Mockito.mock(ResiduoRepository.class);
        UsuarioRepository usuarioRepositoryMock = Mockito.mock(UsuarioRepository.class);

        // Creamos una instancia del servicio de actividades con los mocks
        ActividadService actividadService = new ActividadService(
                actividadRepositoryMock,
                residuoRepositoryMock,
                usuarioRepositoryMock
        );

        // Simulamos la respuesta del repositorio
        Long actividadId = 1L;
        Actividad actividad = new Actividad();
        actividad.setId(actividadId);
        Mockito.when(actividadRepositoryMock.getReferenceById(actividadId)).thenReturn(actividad);

        // Obtenemos la actividad por su ID
        Actividad actividadObtenida = actividadService.getReferenceById(actividadId);

        // Assert que la actividad obtenida sea la misma que la simulada
        assertEquals(actividad, actividadObtenida);
    }

    @Test
    void deleteActividadById() {
        // Creamos mocks de los repositorios necesarios
        ActividadRepository actividadRepositoryMock = Mockito.mock(ActividadRepository.class);
        ResiduoRepository residuoRepositoryMock = Mockito.mock(ResiduoRepository.class);
        UsuarioRepository usuarioRepositoryMock = Mockito.mock(UsuarioRepository.class);

        // Creamos una instancia del servicio de actividades con los mocks
        ActividadService actividadService = new ActividadService(
                actividadRepositoryMock,
                residuoRepositoryMock,
                usuarioRepositoryMock
        );

        // Simulamos la respuesta del repositorio
        Long actividadId = 1L;
        Actividad actividad = new Actividad();
        actividad.setId(actividadId);
        Mockito.when(actividadRepositoryMock.getReferenceById(actividadId)).thenReturn(actividad);

        // Eliminamos la actividad por su ID
        actividadService.deleteActividadById(actividadId);

        // Assert que el método deleteById del repositorio se haya llamado con el ID de la actividad
        Mockito.verify(actividadRepositoryMock).deleteById(actividadId);
    }

    @Test
    void addActividad() {
        // Creamos mocks de los repositorios necesarios
        ActividadRepository actividadRepositoryMock = Mockito.mock(ActividadRepository.class);
        ResiduoRepository residuoRepositoryMock = Mockito.mock(ResiduoRepository.class);
        UsuarioRepository usuarioRepositoryMock = Mockito.mock(UsuarioRepository.class);

        // Creamos una instancia del servicio de actividades con los mocks
        ActividadService actividadService = new ActividadService(
                actividadRepositoryMock,
                residuoRepositoryMock,
                usuarioRepositoryMock
        );

        // Simulamos la respuesta del repositorio
        Long residuoId = 1L;
        Long usuarioId = 1L;
        Actividad actividad = new Actividad();
        actividad.setResiduo(new Residuo(residuoId));
        actividad.setUsuario(new Usuario(usuarioId));
        actividad.setCantidad(10.0);
        Mockito.when(actividadRepositoryMock.save(any(Actividad.class))).thenReturn(actividad);

        // Simulamos la respuesta del repositorio
        Residuo residuo = new Residuo();
        residuo.setPuntos(1.0);
        Mockito.when(residuoRepositoryMock.findById(residuoId)).thenReturn(Optional.of(residuo));

        // Simulamos la respuesta del repositorio
        Usuario usuario = new Usuario();
        usuario.setPuntos(1.0);
        Mockito.when(usuarioRepositoryMock.findById(usuarioId)).thenReturn(Optional.of(usuario));

        // Creamos una instancia de actividad
        Actividad actividadNueva = new Actividad();
        actividadNueva.setResiduo(new Residuo(residuoId));
        actividadNueva.setUsuario(new Usuario(usuarioId));
        actividadNueva.setCantidad(10.0);

        // Registramos la actividad
        ResponseEntity<String> respuesta = actividadService.addActividad(actividadNueva);

        // Assert que la respuesta sea la esperada
        assertEquals("Actividad registrada con éxito. Puntos ganados: 10.0.", respuesta.getBody());
    }
}