package com.upao.recicla.controller;

import com.upao.recicla.domain.dto.residuoDto.*;
import com.upao.recicla.domain.entity.Residuo;
import com.upao.recicla.domain.service.ResiduoService;
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
@RequestMapping("/residuo")
@RequiredArgsConstructor
public class ResiduoController {
    @Autowired
    private final ResiduoService residuoService;

    @PostMapping
    @Transactional
    public ResponseEntity addResiduo(@RequestBody @Valid DatosRegistroResiduo datos,
                                                    UriComponentsBuilder uriBuilder) {
        var residuo = new Residuo(datos);
        residuoService.addResiduo(residuo);

        var uri = uriBuilder.path("/residuo/{id}").buildAndExpand(residuo.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetallesResiduo(residuo));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoResiduo>> getAllResiduos(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(residuoService.getAllResiduos(pageable).map(DatosListadoResiduo::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaResiduo> getResiduoById(@PathVariable Long id) {
        Residuo residuo = residuoService.getReferenceById(id);
        var datosResiduo = new DatosRespuestaResiduo(residuo.getId(), residuo.getNombre(), residuo.getDescripcion(),
                residuo.getTipo(), residuo.getPuntos());
        return ResponseEntity.ok(datosResiduo);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateResiduo(@RequestBody @Valid DatosActualizarResiduo datos) {
        var residuo = residuoService.getReferenceById(datos.id());
        residuo.actualizarResiduo(datos);

        return ResponseEntity.ok(new DatosDetallesResiduo(residuo));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteResiduoById(@PathVariable Long id) {
        var residuo = residuoService.getReferenceById(id);
        residuoService.deleteResiduoById(id);

        return ResponseEntity.noContent().build();
    }

}
