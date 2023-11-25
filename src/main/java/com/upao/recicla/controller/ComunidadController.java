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

    @PostMapping("/nuevo")
    @Transactional
    public ResponseEntity addComunidad(@RequestBody @Valid DatosRegistroComunidad datos,
                                                    UriComponentsBuilder uriBuilder) {
        var comunidad = new Comunidad(datos);
        comunidadService.addComunidad(comunidad);

        var uri = uriBuilder.path("/comunidad/{id}").buildAndExpand(comunidad.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetallesComunidad(comunidad));
    }

    @GetMapping("/mostrar")
    public ResponseEntity<Page<DatosListadoComunidad>> getAllComunidades(@PageableDefault (size = 10) Pageable pageable) {

        return ResponseEntity.ok(comunidadService.getAllComunidades(pageable).map(DatosListadoComunidad::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateComunidad(@RequestBody @Valid DatosActualizarComunidad datos) {
        var comunidad = comunidadService.getReferenceById(datos.id());
        comunidad.actualizarComunidad(datos);

        return ResponseEntity.ok(new DatosDetallesComunidad(comunidad));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteComunidadById(@PathVariable Long id) {
        var comunidad = comunidadService.getReferenceById(id);
        comunidadService.deleteComunidadById(id);

        return ResponseEntity.noContent().build();
    }
}
