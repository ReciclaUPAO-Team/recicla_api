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

    @PostMapping("/registro")
    @Transactional
    public ResponseEntity<Long> addActividad(@RequestBody Actividad actividad) {
        actividadService.addActividad(actividad);
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.path("/{id}").buildAndExpand(actividad.getId());
        return ResponseEntity.created(builder.build().toUri()).body(actividad.getId());
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateActividad(@RequestBody @Valid DatosActualizarActividad datos) {
        var actividad = actividadService.getReferenceById(datos.id());
        actividad.actualizarActividad(datos);

        return ResponseEntity.ok(new DatosDetallesActividad(actividad));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteActividadById(@PathVariable Long id) {
        var actividad = actividadService.getReferenceById(id);
        actividadService.deleteActividadById(id);
        return ResponseEntity.noContent().build();
    }

}

