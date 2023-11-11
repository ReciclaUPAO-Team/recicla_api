package com.upao.recicla.controller;

import com.upao.recicla.domain.dto.comunidadDto.*;
import com.upao.recicla.domain.entity.Comunidad;
import com.upao.recicla.domain.service.ComunidadService;
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
@RequestMapping("/comunidad")
@RequiredArgsConstructor
public class ComunidadController {

    @Autowired
    private final ComunidadService comunidadService;


    @PutMapping
    @Transactional
    public ResponseEntity updateComunidad(@RequestBody @Valid DatosActualizarComunidad datos) {
        var comunidad = comunidadService.getReferenceById(datos.id());
        comunidad.actualizarComunidad(datos);

        return ResponseEntity.ok(new DatosDetallesComunidad(comunidad));
    }

}
