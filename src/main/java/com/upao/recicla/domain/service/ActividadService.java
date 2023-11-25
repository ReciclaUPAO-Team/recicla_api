package com.upao.recicla.domain.service;

import com.upao.recicla.domain.entity.Actividad;
import com.upao.recicla.domain.entity.Residuo;
import com.upao.recicla.domain.entity.Usuario;
import com.upao.recicla.domain.repository.ActividadRepository;
import com.upao.recicla.domain.repository.ResiduoRepository;
import com.upao.recicla.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {
    @Autowired
    private final ActividadRepository actividadRepository;

    @Autowired
    private final ResiduoRepository residuoRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public ActividadService(ActividadRepository actividadRepository,
                            ResiduoRepository residuoRepository,
                            UsuarioRepository usuarioRepository) {
        this.actividadRepository = actividadRepository;
        this.residuoRepository = residuoRepository;
        this.usuarioRepository = usuarioRepository;
    }
    public Page<Actividad> getAllActividades(Pageable pageable) {
        return actividadRepository.findAll(pageable);
    }
    public Optional<Actividad> getActividadById(Long id) {
        return actividadRepository.findById(id);
    }
    public Actividad getReferenceById(Long id) {
        return actividadRepository.getReferenceById(id);
    }
    //registro de actividad con acumulacion de puntos para el usuario
    public ResponseEntity<String> addActividad(Actividad actividad) {
        if (actividad.getResiduo() == null ||
                actividad.getCantidad() <= 0 ||
                actividad.getUsuario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Todos los campos son obligatorios y deben ser válidos.");
        }
        Optional<Residuo> residuoOptional = residuoRepository.findById(actividad.getResiduo().getId());
        if (residuoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El residuo no existe.");
        }
        Residuo residuo = residuoOptional.get();

        double puntosGanados = actividad.getCantidad() * residuo.getPuntos();

        Usuario usuario = usuarioRepository.findById(actividad.getUsuario().getId()).get();

        usuario.setPuntos(usuario.getPuntos() + puntosGanados);
        usuarioRepository.save(usuario);

        actividadRepository.save(actividad);
        return ResponseEntity.ok("Actividad registrada con éxito. Puntos ganados: " + puntosGanados + ".");
    }
    public void updateActividad(Actividad actividad, Long id) {
        Actividad actividadExists = actividadRepository.findById(id)
                .orElse(new Actividad());

        actividadExists.setNombre(actividad.getNombre());
        actividadRepository.save(actividadExists);
    }
    public void deleteActividadById(Long id) {
        actividadRepository.deleteById(id);
    }

}
