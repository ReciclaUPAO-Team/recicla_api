package com.upao.recicla.controller;

import com.upao.recicla.domain.entity.actividad.Actividad;
import com.upao.recicla.domain.entity.usuario.Usuario;
import com.upao.recicla.domain.service.ActividadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actividades")
public class ActividadController {
    private final ActividadService actividadService;

    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }
    @GetMapping
    public List<Actividad> getAllActividades() {
        return actividadService.getAllActividades();
    }
    @GetMapping("/historial/{id}")
    public Actividad getActividadById(@PathVariable Long id) {
        return actividadService.getActividadById(id)
                .orElse(new Actividad());
    }
    @PostMapping
    public void addActividad(@RequestBody Actividad actividad) {
        actividadService.addActividad(actividad);
    }
    @PutMapping("/{id}")
    public void updateActividad(@RequestBody Actividad actividad, @PathVariable Long id) {
        actividadService.updateActividad(actividad, id);
    }
    @DeleteMapping("/{id}")
    public void deleteActividadById(@PathVariable Long id) {
        actividadService.deleteActividadById(id);
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registrarActividad(@RequestBody Actividad actividad) {
        if (actividad.getResiduo() == null ||
                actividad.getCantidadReciclada() <= 0 ||
                actividad.getUsuario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Todos los campos son obligatorios y deben ser válidos.");
        }
        int puntosGanados = actividad.getCantidadReciclada().intValue();

        Usuario usuario = actividad.getUsuario();

        if (usuario.getPuntaje() == null) {
            usuario.setPuntaje(0);
        }
        usuario.setPuntaje(usuario.getPuntaje() + puntosGanados);

        actividadService.addActividad(actividad);

        return ResponseEntity.ok("Actividad de reciclaje registrada. Puntos ganados: " + puntosGanados);
    }

}
