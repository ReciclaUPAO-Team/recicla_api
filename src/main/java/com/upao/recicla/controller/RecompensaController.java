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

    @PostMapping("/crear")
    @Transactional
    public ResponseEntity addRecompensas(@RequestBody @Validated DatosRegistroRecompensa datos,
                                                        UriComponentsBuilder uriBuilder) {
        var recompensa = new Recompensa(datos);
        recompensaService.addRecompensa(recompensa);

        var uri = uriBuilder.path("/recompensa/{id}").buildAndExpand(recompensa.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetallesRecompensa(recompensa));
    }

}
