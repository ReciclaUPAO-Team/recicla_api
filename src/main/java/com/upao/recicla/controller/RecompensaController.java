package com.upao.recicla.controller;

import com.upao.recicla.domain.dto.recompensaDto.*;
import com.upao.recicla.domain.entity.Recompensa;
import com.upao.recicla.domain.service.RecompensaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/recompensa")
@RequiredArgsConstructor
public class RecompensaController {

    @Autowired
    private final RecompensaService recompensaService;


    @GetMapping("/catalogo")
    public ResponseEntity<Page<DatosListadoRecompensa>> listarRecompensas(@PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(recompensaService.getAllRecompensas(pageable).map(DatosListadoRecompensa::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaRecompensa> getRecompensaById(@PathVariable Long id) {
        Recompensa recompensa = recompensaService.getReferenceById(id);
        var datosRecompensa = new DatosRespuestaRecompensa(recompensa.getId(), recompensa.getTitulo(), recompensa.getDescripcion(), recompensa.getCategoria(),
                recompensa.getValor(), recompensa.getFechaInicio(), recompensa.getFechaCierre());
        return ResponseEntity.ok(datosRecompensa);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateRecompensa(@RequestBody @Validated DatosActualizarRecompensa datos) {
        var recompensa = recompensaService.getReferenceById(datos.id());
        recompensa.actualizarRecompensa(datos);

        return ResponseEntity.ok(new DatosDetallesRecompensa(recompensa));
    }


}
