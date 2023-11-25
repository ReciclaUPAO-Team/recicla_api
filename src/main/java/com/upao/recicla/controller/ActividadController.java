package com.upao.recicla.controller;

import com.upao.recicla.domain.dto.actividadDto.DatosActualizarActividad;
import com.upao.recicla.domain.dto.actividadDto.DatosDetallesActividad;
import com.upao.recicla.domain.dto.actividadDto.DatosListadoActividad;
import com.upao.recicla.domain.dto.actividadDto.DatosRespuestaActividad;
import com.upao.recicla.domain.entity.Actividad;
import com.upao.recicla.domain.service.ActividadService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/actividad")
@RequiredArgsConstructor
public class ActividadController {

    @Autowired
    private final ActividadService actividadService;

    @GetMapping
    public ResponseEntity<Page<DatosListadoActividad>> getAllActividades(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(actividadService.getAllActividades(pageable).map(DatosListadoActividad::new));
    }

    @GetMapping("/historial/{id}")
    public ResponseEntity<DatosRespuestaActividad> getHistorialActividades(@PathVariable Long id) {
        Actividad actividad = actividadService.getReferenceById(id);
        var datosActividad = new DatosRespuestaActividad(actividad.getId(), actividad.getNombre(), actividad.getCantidad(),
                actividad.getImagen(), actividad.getFecha(), actividad.getResiduo().getId(), actividad.getResiduo().getNombre(),
                actividad.getResiduo().getDescripcion(), actividad.getResiduo().getTipo(), actividad.getResiduo().getPuntos(),
                actividad.getUsuario().getId(), actividad.getUsuario().getNombre(), actividad.getUsuario().getPuntos());
        return ResponseEntity.ok(datosActividad);
    }

    @PostMapping("/registro")
    @Transactional
    public ResponseEntity<Long> addActividad(@RequestBody Actividad actividad) {
        actividadService.addActividad(actividad);
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.path("/{id}").buildAndExpand(actividad.getId());
        return ResponseEntity.created(builder.build().toUri()).body(actividad.getId());
    }


}

